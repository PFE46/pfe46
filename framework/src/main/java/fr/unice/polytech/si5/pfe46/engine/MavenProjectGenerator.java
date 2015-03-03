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
import fr.unice.polytech.si5.pfe46.engine.inputtype.MavenDependency;
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

	// FIXME
	private static final String DEFAULT_LIBRARY_GROUPID = "com";
	private static final String DEFAULT_LIBRARY_VERSION = "1.0";
	
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
	public void generateMavenProject(UpnpDevice device, Requirements requirements) throws IOException
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
				createZipEntry(out, MAVEN_STRUCTURE_JAVA + service.getName() + ".java", serviceCode.getBytes());
			}

			// Generate the device file
			String serverCode = VelocityCodeGenerator.getIntance().generateServer(device);
			createZipEntry(out, MAVEN_STRUCTURE_JAVA + device.getDeviceName() + "Server.java", serverCode.getBytes());

			// Generate pom.xml
			Set<MavenDependency> dependencies = new HashSet<>(); // TODO
			if (requirements.getMavenDependencies() != null)
			{
				dependencies.addAll(requirements.getMavenDependencies());
			}

			if (requirements.getLocalJars() != null)
			{
				for (String path : requirements.getLocalJars())
				{
					File jar = new File(path);
					addLibrary(out, jar);

					MavenDependency dep = new MavenDependency();
					dep.setGroupId(DEFAULT_LIBRARY_GROUPID);
					dep.setArtifactId(jar.getName().substring(0, jar.getName().lastIndexOf(".")));
					dep.setVersion(DEFAULT_LIBRARY_VERSION);
					dep.setLocalJar(true);
					dependencies.add(dep);
				}
			}

			String pomXmlCode = VelocityCodeGenerator.getIntance().generatePomXml(device, dependencies);
			createZipEntry(out, MAVEN_POM_XML, pomXmlCode.getBytes());

			// Add modules
			if (requirements.getJavaModules() != null)
			{
				for (String path : requirements.getJavaModules())
				{
					File module = new File(path);
					addModule(out, module);
				}
			}

			//
			// TODO: gérer les imports
			//
			
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
	 * If the given file is a file then add it to the given ZipOutputStream,
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
				// FIXME: remove MainModule.java
				if (!f.getName().equals("MainModule.java"))
				{
					addModule(out, f);
				}
			}
		}
		else
		{
			String fileName = "/src/main/java/modules/" + file.getName();
			
			createZipEntry(out, fileName, Files.readAllBytes(Paths.get(file.getAbsolutePath())));		}
	}

	/**
	 * Add library to the generated local Maven repository.
	 * @param out ZipOutputStream
	 * @param file Library
	 * @throws IOException If an IOException is thrown.
	 */
	private void addLibrary(ZipOutputStream out, File file) throws IOException
	{
		String groupId = DEFAULT_LIBRARY_GROUPID; // FIXME
		String version = DEFAULT_LIBRARY_VERSION; // FIXME
		String artifactId = file.getName().substring(0, file.getName().lastIndexOf(".")); // remove file extension
		
		String fileName = "/localrepo/" + groupId + "/" + artifactId + "/" + version + "/" + artifactId + "-" + version + ".jar";

		createZipEntry(out, fileName, Files.readAllBytes(Paths.get(file.getAbsolutePath())));
	}

	/**
	 * Create a zip entry in out with filename and content.
	 * 
	 * @param out ZipOutputStream.
	 * @param fileName Name of the generated file.
	 * @param content Content of the generated file.
	 * @throws IOException If an IOException is thrown.
	 */
	private void createZipEntry(ZipOutputStream out, String fileName, byte[] content) throws IOException
	{
		ZipEntry entry = new ZipEntry(fileName);
		entry.setMethod(ZipEntry.DEFLATED);
		out.putNextEntry(entry);
		out.write(content);
	}

}
