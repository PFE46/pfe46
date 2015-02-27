package fr.unice.polytech.si5.pfe46.engine.inputtype.methods;

public class WsRestMethodBinding extends MethodBinding {

	private String endpoint;
	private WsRestVerb verb;

	public enum WsRestVerb {
		GET, POST, PUT;
	}

	public String getEndpoint() { return endpoint; }
	public void setEndpoint(String endpoint) { this.endpoint = endpoint; }
	
	public WsRestVerb getVerb() { return verb; }
	public void setVerb(WsRestVerb verb) { this.verb = verb; }
	
}
