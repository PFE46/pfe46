package fr.unice.polytech.si5.pfe46.engine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import fr.unice.polytech.si5.pfe46.Config;
import fr.unice.polytech.si5.pfe46.templating.VelocityCodeGenerator;
import fr.unice.polytech.si5.pfe46.templating.components.MavenDependency;
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
			Set<MavenDependency> dependencies = new HashSet<>(); // TODO
			
			//
			// FIXME: HARD CODED //
			//
				MavenDependency bluecove = new MavenDependency();
				bluecove.setGroupId("net.sf.bluecove");
				bluecove.setArtifactId("bluecove");
				bluecove.setVersion("2.1.0");
				
				MavenDependency wiiRemoteJ = new MavenDependency();
				wiiRemoteJ.setGroupId("wiiremotej");
				wiiRemoteJ.setArtifactId("wiiremotej");
				wiiRemoteJ.setVersion("1.0");
				wiiRemoteJ.setScope("system");
				wiiRemoteJ.setSystemPath("${basedir}/src/main/resources/lib/WiiRemoteJ.jar");
				
				dependencies.add(bluecove);
				dependencies.add(wiiRemoteJ);
				addLibrary(out, new File("src/main/resources/WiiBalance/WiiRemoteJ.jar"));
				addModule(out, new File("src/main/resources/WiiBalance/BBImpl.java"));
			//
			// FIXME: HARD CODED //
			//
				
			String pomXmlCode = VelocityCodeGenerator.getIntance().generatePomXml(device, dependencies);
			createZipEntry(out, MAVEN_POM_XML, pomXmlCode);
			
			// Add modules
			// FIXME: ugly hack
			File modulesDirectory = new File("src/main/java/fr/unice/polytech/si5/pfe46/" + MODULES_FOLDER);
			addModule(out, modulesDirectory);
			File jsonProcess = new File("src/main/java/fr/unice/polytech/si5/pfe46/utils/JsonProcess.java");
			addModule(out, jsonProcess);
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
				// FIXME: remove MainModules.java
				if (!f.getName().equals("MainModule.java"))
				{
					addModule(out, f);
				}
			}
		}
		else
		{
			// FIXME: ugly hack
			//
			// absolute path is like "/Users/victorsalle/Cours/PFE/pfe46/framework/src/main/java/fr/unice/polytech/si5/pfe46/modules/File.java"
			// split gives ["/Users/victorsalle/Cours/PFE/pfe46/framework/src/main/java/", "modules/File.java"]
			// split[1] = "modules/File.java"
			// -> equivalent to the file structure to embed in the zip file

			String fileName;
			ZipEntry entry;
			try
			{
				fileName = file.getAbsolutePath().split("fr/unice/polytech/si5/pfe46/")[1];
				entry = new ZipEntry("/src/main/java/" + fileName);
			}
			catch (Exception e) // FIXME: ugly hack
			{
				entry = new ZipEntry("/src/main/java/modules/" + file.getName());
			}
			
			entry.setMethod(ZipEntry.DEFLATED);
			out.putNextEntry(entry);
			out.write(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
		}
	}
	
	private void addLibrary(ZipOutputStream out, File file) throws IOException
	{
		ZipEntry entry = new ZipEntry("/src/main/resources/lib/" + file.getName());
		
		entry.setMethod(ZipEntry.DEFLATED);
		out.putNextEntry(entry);
		out.write(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
	}
	
}
