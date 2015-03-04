package fr.unice.polytech.si5.pfe46.engine.inputtype;

public class MavenDependency {

	private String groupId;
	private String artifactId;
	private String version;
	private boolean localJar = false;

	public String getGroupId() { return groupId; }
	public void setGroupId(String groupId) { this.groupId = groupId; }

	public String getArtifactId() { return artifactId; }
	public void setArtifactId(String artifactId) { this.artifactId = artifactId; }

	public String getVersion() { return version; }
	public void setVersion(String version) { this.version = version; }
	
	public boolean isLocalJar() { return localJar; }
	public void setLocalJar(boolean localJar) { this.localJar = localJar; }

}
