package fr.unice.polytech.si5.pfe46.modules;

import java.io.File;

public enum Modules {

	WS_HANDLER("WSHandler"),
	OAUTH_HANDLER("OAuth" + File.separator + "OAuthHandler"),
	OAUTH_WITHINGS("OAuth" + File.separator + "API" + File.separator + "WithingsApi"),
	OAUTH_EXCEPTION_NO_PROVIDER("OAuth" + File.separator + "exceptions" + File.separator + "NoSuchProviderException");
	
	String fileName;
	
	Modules(String fileName)
	{
		this.fileName = fileName + ".java";
	}
	
	public String getFileName()
	{
		return fileName;
	}
	
}
