package fr.unice.polytech.si5.pfe46.templating;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;
import java.util.Set;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import fr.unice.polytech.si5.pfe46.Config;
import fr.unice.polytech.si5.pfe46.engine.inputtype.MavenDependency;
import fr.unice.polytech.si5.pfe46.templating.components.UpnpDevice;
import fr.unice.polytech.si5.pfe46.templating.components.UpnpService;

/**
 * Class used to generate Java files that uses Cling library from template and Upnp{Service,Device...}.
 * 
 * @author victorsalle
 */
public class VelocityCodeGenerator {

	private static VelocityCodeGenerator INSTANCE;
	private VelocityEngine velocityEngine;
	
	/**
	 * Singleton accessor.
	 * 
	 * @return VelocityGenerator instance.
	 * @throws IOException 
	 */
	public static VelocityCodeGenerator getIntance() throws IOException
	{
		if (INSTANCE == null)
		{
			INSTANCE = new VelocityCodeGenerator();
		}
		return INSTANCE;
	}
	
	
	/**
	 * Private constructor (singleton).
	 * 
	 * @throws IOException 
	 */
	private VelocityCodeGenerator() throws IOException
	{
		// Load properties file
		Properties properties = new Properties();
		properties.load(getClass().getClassLoader().getResourceAsStream(Config.VELOCITY_PROPERTIES));
		
		// Instantiate the engine
		velocityEngine = new VelocityEngine();
		velocityEngine.init(properties);
	}
	
	/**
	 * Generate Java code compatible with Cling library for a service.
	 * 
	 * @param service Service to generate.
	 * @return Generated code.
	 */
	public String generateService(UpnpService service)
	{
		// Retrieve the template
		Template template = velocityEngine.getTemplate(Config.VELOCITY_TEMPLATE_SERVICE);
		
		// Data to fill the template with
		VelocityContext context = new VelocityContext();
		context.put("service", service);
		
		// Fill the template
		StringWriter writer = new StringWriter();
		template.merge(context, writer);
		
		return formatJavaCode(writer.toString());
	}

	/**
	 * Generate Java code compatible with Cling library for a service.
	 * 
	 * @param device Device to generate.
	 * @return Generated code.
	 */
	public String generateServer(UpnpDevice device)
	{
		// Retrieve the template
		Template template = velocityEngine.getTemplate(Config.VELOCITY_TEMPLATE_SERVER);

		// Data to fill the template with
		VelocityContext context = new VelocityContext();
		context.put("device", device);

		// Fill the template
		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		return formatJavaCode(writer.toString());
	}

	/**
	 * Generate Pom.XML for the Maven project.
	 * 
	 * @param device Device.
	 * @param dependencies Set of Maven dependencies.
	 * @return Generated code.
	 */
	public String generatePomXml(UpnpDevice device, Set<MavenDependency> dependencies)
	{
		// Retrieve the template
		Template template = velocityEngine.getTemplate(Config.VELOCITY_TEMPLATE_POMXML);

		// Data to fill the template with
		VelocityContext context = new VelocityContext();
		context.put("device", device);
		context.put("dependencies", dependencies);

		// Fill the template
		StringWriter writer = new StringWriter();
		template.merge(context, writer);
		
		return writer.toString();
	}
	
	/**
	 * Format a Java code.
	 * 
	 * @param code Code to format.
	 * @return Formatted code or original code if an error occurred.
	 */
	private String formatJavaCode(String code)
	{
		// TODO
		
		return code;
	}
	
}
