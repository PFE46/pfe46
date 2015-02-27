package fr.unice.polytech.si5.pfe46.templating.components;

public class MavenDependency {

	private String groupId;
	private String artifactId;
	private String version;
	private String scope;
	private String systemPath;

	public String getGroupId() { return groupId; }
	public void setGroupId(String groupId) { this.groupId = groupId; }

	public String getArtifactId() { return artifactId; }
	public void setArtifactId(String artifactId) { this.artifactId = artifactId; }

	public String getVersion() { return version; }
	public void setVersion(String version) { this.version = version; }

	public String getScope() { return scope; }
	public void setScope(String scope) { this.scope = scope; }
	
	public String getSystemPath() { return systemPath; }
	public void setSystemPath(String systemPath) { this.systemPath = systemPath; }
	
}
