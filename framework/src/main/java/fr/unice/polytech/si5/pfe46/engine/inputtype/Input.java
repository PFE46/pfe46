package fr.unice.polytech.si5.pfe46.engine.inputtype;

import java.util.List;

import fr.unice.polytech.si5.pfe46.engine.inputtype.methods.Method;
import fr.unice.polytech.si5.pfe46.engine.inputtype.objects.ConnectedObject;

public class Input {

	private List<ConnectedObject> objects;
	private List<Method> methods;
	private List<MavenDependency> mavenDependencies;
	private List<String> localJars;
	private List<String> javaModules;
	
	public List<ConnectedObject> getObjects() { return objects; }
	public void setObjects(List<ConnectedObject> objects) { this.objects = objects; }
	
	public List<Method> getMethods() { return methods; }
	public void setMethods(List<Method> methods) { this.methods = methods; }

	public List<MavenDependency> getMavenDependencies() { return mavenDependencies; }
	public void setMavenDependencies(List<MavenDependency> mavenDependencies) { this.mavenDependencies = mavenDependencies; }

	public List<String> getLocalJars() { return localJars; }
	public void setLocalJars(List<String> localJars) { this.localJars = localJars; }
	
	public List<String> getJavaModules() { return javaModules; }
	public void setJavaModules(List<String> javaModules) { this.javaModules = javaModules; }
	
}
