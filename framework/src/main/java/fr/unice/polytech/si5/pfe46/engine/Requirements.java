package fr.unice.polytech.si5.pfe46.engine;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import fr.unice.polytech.si5.pfe46.engine.inputtype.MavenDependency;

public class Requirements {

	private Set<MavenDependency> mavenDependencies;
	private Set<String> localJars;
	private Set<String> javaModules;
	
	public void addMavenDependency(MavenDependency mavenDependency)
	{
		if (mavenDependencies == null)
		{
			mavenDependencies = new HashSet<>();
		}
		mavenDependencies.add(mavenDependency);
	}
	
	public void addMavenDependencies(Collection<MavenDependency> collection)
	{
		if (mavenDependencies == null)
		{
			mavenDependencies = new HashSet<>();			
		}
		if (collection != null)
		{
			mavenDependencies.addAll(collection);
		}
	}
	
	public void addLocalJar(String localJarPath)
	{
		if (localJars == null)
		{
			localJars = new HashSet<>();
		}
		localJars.add(localJarPath);
	}
	
	public void addLocalJars(Collection<String> collection)
	{
		if (localJars == null)
		{
			localJars = new HashSet<>();			
		}
		if (collection != null)
		{
			localJars.addAll(collection);
		}
	}
	
	public void addJavaModule(String javaModulePath)
	{
		if (javaModules == null)
		{
			javaModules = new HashSet<>();
		}
		javaModules.add(javaModulePath);
	}
	
	public void addJavaModules(Collection<String> collection)
	{
		if (javaModules == null)
		{
			javaModules = new HashSet<>();			
		}
		if (collection != null)
		{
			javaModules.addAll(collection);
		}
	}
	
	public Set<MavenDependency> getMavenDependencies() { return mavenDependencies; }
	public void setMavenDependencies(Set<MavenDependency> mavenDependencies) { this.mavenDependencies = mavenDependencies; }
	
	public Set<String> getLocalJars() { return localJars; }
	public void setLocalJars(Set<String> localJars) { this.localJars = localJars; }
	
	public Set<String> getJavaModules() { return javaModules; }
	public void setJavaModules(Set<String> javaModules) { this.javaModules = javaModules; }
	
}
