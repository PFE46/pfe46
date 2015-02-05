package fr.unice.polytech.si5.pfe46.templating.engine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import fr.unice.polytech.si5.pfe46.Config;
import fr.unice.polytech.si5.pfe46.templating.components.UpnpDevice;
import fr.unice.polytech.si5.pfe46.templating.components.UpnpService;

/**
 * This class is used to generate a Maven project for a UpnpDevice.
 * 
 * @author victorsalle
 */
public class MavenProjectGenerator {

	private VelocityGenerator generator;
	
	private static final String MAVEN_STRUCTURE_JAVA = "src" + File.separator + "main" + File.separator + "java" + File.separator;
	private static final String MAVEN_POM_XML = "pom.xml";
	
	/**
	 * Constructor.
	 * 
	 * @throws IOException 
	 */
	public MavenProjectGenerator() throws IOException
	{
		generator = new VelocityGenerator();
	}
	
	/**
	 * Generate a zip file containing a Maven project.
	 * 
	 * @param device Device used to generate the Maven project.
	 * @throws IOException If an IOException is thrown.
	 */
	public void generateMavenProject(UpnpDevice device) throws IOException
	{
		ZipOutputStream out = null;
		File zipProject = null;
		
		try
		{
			zipProject = new File(Config.GENERATED_FILE_NAME);
			out = new ZipOutputStream(new FileOutputStream(zipProject));
		
			// Generate services files
			for (UpnpService service : device.getServices())
			{
				String serviceCode = generator.generateService(service);
				createZipEntry(out, MAVEN_STRUCTURE_JAVA + service.getName() + ".java", serviceCode);
			}
			
			// Generate the device file
			String serverCode = generator.generateServer(device);
			createZipEntry(out, MAVEN_STRUCTURE_JAVA + device.getDeviceName() + "Server.java", serverCode);
			
			// Generate pom.xml
			String pomXmlCode = generator.generatePomXml(device);
			createZipEntry(out, MAVEN_POM_XML, pomXmlCode);
		}
		finally
		{
			out.close();
		}
	}
	
	/**
	 * Create a zip entry in out with filename and content.
	 * 
	 * @param out ZipOutputStream.
	 * @param fileName Name of the generated file.
	 * @param content Content of the generated file.
	 * @throws IOException If an IOException is thrown.
	 */
	private void createZipEntry(ZipOutputStream out, String fileName, String content) throws IOException
	{
		ZipEntry entry = new ZipEntry(fileName);
		entry.setMethod(ZipEntry.DEFLATED);
		out.putNextEntry(entry);
		out.write(content.getBytes());
	}
	
}
