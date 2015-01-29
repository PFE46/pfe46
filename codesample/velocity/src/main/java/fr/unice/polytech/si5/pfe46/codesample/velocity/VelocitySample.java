package fr.unice.polytech.si5.pfe46.codesample.velocity;

import java.io.StringWriter;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class VelocitySample 
{
    public static void main( String[] args )
    {
    	// Properties to set 'src/main/resources' as Velocity template root folder
    	Properties properties = new Properties();
    	properties.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

    	// Create the engine
    	VelocityEngine engine = new VelocityEngine();
        engine.init(properties);
        
        Template template = engine.getTemplate("velocity_templates/template.vm");
        
        // Values to fill the template 
        VelocityContext context = new VelocityContext();
        context.put("name", "Victor");
        
        // Fill the template
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        
        System.out.println(writer.toString());
    }
}
