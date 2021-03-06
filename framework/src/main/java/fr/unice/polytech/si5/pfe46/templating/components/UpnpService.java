package fr.unice.polytech.si5.pfe46.templating.components;

import java.util.LinkedHashSet;
import java.util.Set;

import fr.unice.polytech.si5.pfe46.templating.exceptions.DuplicateMethodSignatureException;
import fr.unice.polytech.si5.pfe46.templating.exceptions.UpnpStateVariableConflictException;
import fr.unice.polytech.si5.pfe46.utils.Pair;

/**
 * This class represents a UpnpService and therefore embeds several UpnpMethod to expose.
 * 
 * @author victorsalle
 */
public class UpnpService {

	private String name;
	private Set<UpnpMethod> methods;

	//
	// CONSTRUCTORS
	//
	
	/**
	 * Empty constructor.
	 */
	public UpnpService()
	{
		methods = new LinkedHashSet<UpnpMethod>();
	}

	/**
	 * Constructor of a UpnpService.
	 * 
	 * @param name Name of the service.
	 * @param methods Methods of the service.
	 */
	public UpnpService(String name, Set<UpnpMethod> methods)
	{
		this();
		this.name = name;
		
		if (methods != null)
		{
			this.methods = methods;
		}
	}

	//
	// METHOD
	//

	/**
	 * Add a UpnpMethod to this service.
	 * 
	 * @param method Method to add.
	 * @throws UpnpStateVariableConflictException If there is a conflict between service's state variables and one of the given method.
	 * @throws DuplicateMethodSignatureException If the method's signature already exists in this service.
	 */
	public void addMethod(UpnpMethod method) throws UpnpStateVariableConflictException, DuplicateMethodSignatureException
	{
		// Check if the method's signature doesn't exist yet
		for (UpnpMethod existingMethod : methods)
		{
			if (method.sameSignature(existingMethod))
			{
				throw new DuplicateMethodSignatureException(method);
			}
		}
		
		// Check if there is no conflict between existing state variables and one of the given method
		Pair<UpnpStateVariable, UpnpStateVariable> conflict = UpnpStateVariable.checkNoConflict(this.getStateVariables(), method.getStateVariables());

		if (conflict != null)
		{
			throw new UpnpStateVariableConflictException(conflict.first, conflict.second);
		}

		// Add the method
		this.methods.add(method);
	}

	/**
	 * Retrieve all state variables from service's methods.
	 * 
	 * @return Service's state variables.
	 */
	public Set<UpnpStateVariable> getStateVariables()
	{
		Set<UpnpStateVariable> stateVariables = new LinkedHashSet<UpnpStateVariable>();

		for (UpnpMethod method : methods)
		{
			stateVariables.addAll(method.getStateVariables());
		}

		return stateVariables;
	}

	//
	// GETTERS & SETTERS
	//
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the methods
	 */
	public Set<UpnpMethod> getMethods() {
		return methods;
	}

	/**
	 * @param methods the methods to set
	 */
	public void setMethods(Set<UpnpMethod> methods) {
		this.methods = methods;
	}

}
