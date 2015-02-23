package fr.unice.polytech.si5.pfe46.modules.OAuth.exceptions;

public class NoSuchProviderException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoSuchProviderException(String provider)
	{
		super("NoSuchProviderException: " + provider);
	}
	
}
