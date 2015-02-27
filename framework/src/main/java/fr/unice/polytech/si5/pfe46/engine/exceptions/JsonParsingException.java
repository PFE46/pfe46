package fr.unice.polytech.si5.pfe46.engine.exceptions;

public class JsonParsingException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public JsonParsingException(Exception e)
	{
		super("JsonParsingException: " + e.getMessage());
	}

}
