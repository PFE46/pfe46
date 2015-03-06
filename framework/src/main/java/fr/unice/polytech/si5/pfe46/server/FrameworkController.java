package fr.unice.polytech.si5.pfe46.server;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fr.unice.polytech.si5.pfe46.engine.InputParser;
import fr.unice.polytech.si5.pfe46.engine.InputToUpnpDevice;
import fr.unice.polytech.si5.pfe46.engine.MavenProjectGenerator;
import fr.unice.polytech.si5.pfe46.engine.Requirements;
import fr.unice.polytech.si5.pfe46.engine.exceptions.JsonParsingException;
import fr.unice.polytech.si5.pfe46.engine.inputtype.Input;
import fr.unice.polytech.si5.pfe46.templating.components.UpnpDevice;
import fr.unice.polytech.si5.pfe46.templating.exceptions.DuplicateMethodSignatureException;
import fr.unice.polytech.si5.pfe46.templating.exceptions.UpnpStateVariableConflictException;
import fr.unice.polytech.si5.pfe46.utils.Pair;

@Controller
public class FrameworkController {

	/**
	 * GET /project
	 */
	@RequestMapping(value = "/generator", method = RequestMethod.GET)
	public String get(Model model)
	{
		model.addAttribute("jsonInput", new JsonInput());
		return "form"; // src/main/resources/templates/form.html
	}
	
	/**
	 * POST /project.zip
	 * @throws URISyntaxException 
	 */
	@RequestMapping(value = "/project.zip", method = RequestMethod.POST)
	public HttpEntity<byte[]> post(@ModelAttribute JsonInput jsonInput, Model model)//, HttpServletResponse response)
			throws IOException, JsonParsingException, UpnpStateVariableConflictException, DuplicateMethodSignatureException, URISyntaxException
	{
		// Parse input
		Input input = InputParser.getInstance().parse(jsonInput.getValue());
		
		// Get UpnpDevice and Requirements
		Pair<UpnpDevice, Requirements> device = InputToUpnpDevice.getInstance().getDevice(input);
		UpnpDevice upnpDevice = device.first;
		Requirements requirements = device.second;

		// Generate Maven project
		byte[] project = MavenProjectGenerator.getInstance().generateMavenProject(upnpDevice, requirements);
		
		// Return it
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentLength(project.length);
		
		return new HttpEntity<>(project, headers);
	}
	
	
}
