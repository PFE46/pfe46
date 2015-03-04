package fr.unice.polytech.si5.pfe46;

import java.io.File;

/**
 * Configuration class.
 * 
 * @author victorsalle
 */
public class Config {

	// General
	
	public static final String PROPERTIES_FOLDER = "properties";
	
	// Generated file
	
	public static final String GENERATED_FILE_NAME = "project.zip";
	
	// Velocity
	
	public static final String VELOCITY_PROPERTIES = PROPERTIES_FOLDER + File.separator + "velocity.properties";
	
	public static final String VELOCITY_TEMPLATE_FOLDER = "velocity_templates";
	
	public static final String VELOCITY_TEMPLATE_METHOD = VELOCITY_TEMPLATE_FOLDER + File.separator + "method.vm";
	public static final String VELOCITY_TEMPLATE_SERVICE = VELOCITY_TEMPLATE_FOLDER + File.separator + "service.vm";
	public static final String VELOCITY_TEMPLATE_SERVER = VELOCITY_TEMPLATE_FOLDER + File.separator + "server.vm";
	public static final String VELOCITY_TEMPLATE_POMXML = VELOCITY_TEMPLATE_FOLDER + File.separator + "pom.xml.vm";
	
}
