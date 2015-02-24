package fr.unice.polytech.si5.pfe46.engine.inputtype.objects;

public class WsRestObject extends ConnectedObject {

	private boolean useOAuth;
	private String provider;
	
	public boolean isUseOAuth() { return useOAuth; }
	public void setUseOAuth(boolean useOAuth) { this.useOAuth = useOAuth; }
	
	public String getProvider() { return provider; }
	public void setProvider(String provider) { this.provider = provider; }
	
}
