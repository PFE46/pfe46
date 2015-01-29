package fr.unice.polytech.si5.pfe46.templating.exceptions;

import fr.unice.polytech.si5.pfe46.templating.components.UpnpStateVariable;

/**
 * Exception to throw if the user wants to add a state variable that already exists
 * in the current context.
 * 
 * @author victorsalle
 */
public class UpnpStateVariableConflictException extends Exception {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param first A state variable.
	 * @param second A state variable that conflicts with the first one.
	 */
	public UpnpStateVariableConflictException(UpnpStateVariable first, UpnpStateVariable second)
	{
		super("UpnpStateVariable " + first + " conflicts with " + second);
	}

}
