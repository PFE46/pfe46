package fr.unice.polytech.si5.pfe46.templating.exceptions;

import fr.unice.polytech.si5.pfe46.templating.components.UpnpMethod;

/**
 * Exception to throw if the user wants to add a method to a service and the
 * method's signature already exists in the service.
 * 
 * @author victorsalle
 */
public class DuplicateMethodSignatureException extends Exception {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param first A state variable.
	 * @param second A state variable that conflicts with the first one.
	 */
	public DuplicateMethodSignatureException(UpnpMethod method)
	{
		super("DuplicateMethodSignatureException: "
				+ (method.hasOutput() ?
						method.getOutput().getDatatype().getJavaType()
						: "void")
				+ " " + method.getName());
	}

}
