package fr.unice.polytech.si5.pfe46.engine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import fr.unice.polytech.si5.pfe46.Config;
import fr.unice.polytech.si5.pfe46.templating.VelocityCodeGenerator;
import fr.unice.polytech.si5.pfe46.templating.components.UpnpDevice;
import fr.unice.polytech.si5.pfe46.templating.components.UpnpService;

/**
 * This class is used to generate a Maven project for a UpnpDevice.
 * 
 * @author victorsalle
 */
public class MavenProjectGenerator {

	private static MavenProjectGenerator INSTANCE;
	
	private static final String MAVEN_STRUCTURE_JAVA = "src" + File.separator + "main" + File.separator + "java" + File.separator;
	private static final String MAVEN_POM_XML = "pom.xml";
	private static final String MODULES_FOLDER = "modules";
	
	/**
	 * Singleton accessor.
	 * 
	 * @return MavenProjectGenerator instance.
	 */
	public static MavenProjectGenerator getInstance() throws IOException
	{
		if (INSTANCE == null)
		{
			INSTANCE = new MavenProjectGenerator();
		}
		return INSTANCE;
	}
	
	/**
	 * Private constructor (singleton).
	 */
	private MavenProjectGenerator()
	{
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
				String serviceCode = VelocityCodeGenerator.getIntance().generateService(service);
				createZipEntry(out, MAVEN_STRUCTURE_JAVA + service.getName() + ".java", serviceCode);
			}
			
			// Generate the device file
			String serverCode = VelocityCodeGenerator.getIntance().generateServer(device);
			createZipEntry(out, MAVEN_STRUCTURE_JAVA + device.getDeviceName() + "Server.java", serverCode);
			
			// Generate pom.xml
			String pomXmlCode = VelocityCodeGenerator.getIntance().generatePomXml(device);
			createZipEntry(out, MAVEN_POM_XML, pomXmlCode);
			
			// Add modules
			File modulesDirectory = new File("src/main/resources/" + MODULES_FOLDER);
			addModule(out, modulesDirectory);
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
	
	/**
	 * If the given file is a file then add it to the given zipoutputstream,
	 * otherwise if it is a directory then add each module that it contains.
	 * 
	 * @param out ZipOutputStream
	 * @param file Name of the existing 
	 * @throws IOException If an IOException is thrown.
	 */
	private void addModule(ZipOutputStream out, File file) throws IOException
	{
		if (file.isDirectory())
		{
			for (File f : file.listFiles())
			{
				addModule(out, f);
			}
		}
		else
		{
			// #WTF
			//
			// FIXME:
			// absolute path is like "/Users/victorsalle/Cours/PFE/pfe46/framework/src/main/resources/modules/package/File.java"
			// split gives ["/Users/victorsalle/Cours/PFE/pfe46/", "/src/main/resources/modules/package/File.java"]
			// split[1] = "/src/main/resources/modules/package/File.java"
			// replacing all "resources" by "java" returns "/src/main/java/modules/package/File.java"
			// equivalent to the Maven file structure to embed in the zip file
			ZipEntry entry = new ZipEntry(file.getAbsolutePath().split("framework")[1].replaceAll("resources", "java"));
			entry.setMethod(ZipEntry.DEFLATED);
			out.putNextEntry(entry);
			out.write(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
		}
	}
	
}
