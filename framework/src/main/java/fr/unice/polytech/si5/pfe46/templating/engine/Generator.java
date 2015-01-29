package fr.unice.polytech.si5.pfe46.templating.engine;

import java.io.File;
import java.io.StringWriter;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import fr.unice.polytech.si5.pfe46.Config;
import fr.unice.polytech.si5.pfe46.templating.components.UpnpDevice;
import fr.unice.polytech.si5.pfe46.templating.components.UpnpService;

/**
 * Class used to generate Java files that uses Cling library from template and Upnp{Service,Device...}.
 * 
 * @author victorsalle
 */
public class Generator {

	VelocityEngine velocityEngine;
	
	/**
	 * Constructor.
	 */
	public Generator()
	{
    	// Properties to set 'src/main/resources' as Velocity template root folder
    	Properties properties = new Properties();
    	properties.setProperty(
    			"file.resource.loader.class",
    			"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

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
		Template template = velocityEngine
				.getTemplate(Config.VELOCITY_TEMPLATE_FOLDER + File.separator + Config.VELOCITY_TEMPLATE_SERVICE);
		
		VelocityContext context = new VelocityContext();
		context.put("service", service);
		
		StringWriter writer = new StringWriter();
		template.merge(context, writer);
		
		return writer.toString();
	}

	/**
	 * Generate Java code compatible with Cling library for a service.
	 * 
	 * @param device Device to generate.
	 * @return Generated code.
	 */
	public String generateServer(UpnpDevice device)
	{
		Template template = velocityEngine
				.getTemplate(Config.VELOCITY_TEMPLATE_FOLDER + File.separator + Config.VELOCITY_TEMPLATE_SERVER);
		
		VelocityContext context = new VelocityContext();
		context.put("device", device);
		
		StringWriter writer = new StringWriter();
		template.merge(context, writer);
		
		return writer.toString();
	}

	/**
	 * Generate Pom.XML for the Maven project.
	 * 
	 * @param device Device.
	 * @return Generated code.
	 */
	public String generatePomXml(UpnpDevice device)
	{
		Template template = velocityEngine
				.getTemplate(Config.VELOCITY_TEMPLATE_FOLDER + File.separator + Config.VELOCITY_TEMPLATE_POMXML);
		
		VelocityContext context = new VelocityContext();
		context.put("device", device);
		
		StringWriter writer = new StringWriter();
		template.merge(context, writer);
		
		return writer.toString();
	}
	
}
