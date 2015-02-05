package fr.unice.polytech.si5.pfe46.templating.exceptions;

import fr.unice.polytech.si5.pfe46.templating.components.UpnpMethod;
import fr.unice.polytech.si5.pfe46.templating.components.UpnpStateVariable;

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
				+ " " + method.getName()
				+ " ("
				+ getParams(method)
				+ ")"
				);
	}
	
	/**
	 * Returns the string representation of method's parameters. 
	 * 
	 * @param method Method.
	 * @return String representation of method's parameters.
	 */
	private static String getParams(UpnpMethod method)
	{
		StringBuilder builder = new StringBuilder();
		for (UpnpStateVariable input : method.getInputs())
		{
			if (builder.length() != 0) // if not first item
			{
				builder.append(", ");
			}
			builder.append(input.getDatatype().getJavaType());
			builder.append(" ");
			builder.append(input.getName());
		}
		return builder.toString();
	}

}
