package fr.unice.polytech.si5.pfe46.templating.components;

import java.util.HashSet;
import java.util.Set;

import fr.unice.polytech.si5.pfe46.templating.exceptions.UpnpStateVariableConflictException;

/**
 * This class represents a UpnpMethod. A method has a name, several lines of code in
 * its body, parameters, return type, exceptions that can be thrown...
 * 
 * @author victorsalle
 */
public class UpnpMethod {

	private String name;
	private String content;
	private Set<UpnpStateVariable> inputs;
	private UpnpStateVariable output;
	private Set<Class<? extends Exception>> exceptions;

	//
	// CONSTRUCTORS
	//
	
	/**
	 * Empty constructor.
	 */
	public UpnpMethod()
	{
		this.inputs = new HashSet<UpnpStateVariable>();
		this.exceptions = new HashSet<Class<? extends Exception>>();
	}
	
	/**
	 * Constructor of a method.
	 * 
	 * @param name Name of the method.
	 * @param content Body of the method (lines of code).
	 * @param inputs Parameters of the method.
	 * @param output Return of the method.
	 * @param exceptions Exceptions that the method can throw.
	 */
	public UpnpMethod(String name, String content, Set<UpnpStateVariable> inputs, UpnpStateVariable output, Set<Class<? extends Exception>> exceptions)
	{
		this();
		this.name = name;
		this.content = content;
		this.output = output;

		if (inputs != null)
		{
			this.inputs = inputs;
		}
		if (exceptions != null)
		{
			this.exceptions = exceptions;
		}
	}
	
	//
	// METHODS
	//
	
	/**
	 * Add a parameter to this method.
	 * 
	 * @param stateVariable Parameter to add.
	 * @throws UpnpStateVariableConflictException If there is a conflict between method's state variables and the parameter to add.
	 */
	public void addInput(UpnpStateVariable stateVariable) throws UpnpStateVariableConflictException
	{
		// Check if there is no conflict between existing state variables and the given one
		UpnpStateVariable conflict = UpnpStateVariable.checkNoConflict(stateVariable, getStateVariables());

		if (conflict != null)
		{
			throw new UpnpStateVariableConflictException(stateVariable, conflict);
		}
		
		// Add the state variable
		this.inputs.add(stateVariable);
	}
	
	/**
	 * Add an exception that the method can throw.
	 * 
	 * @param exception Exception to add.
	 */
	public void addException(Class<? extends Exception> exception)
	{
		this.exceptions.add(exception);
	}
	
	/**
	 * Returns if the method has inputs parameters.
	 * 
	 * @return True if the method has parameters, false otherwise.
	 */
	public boolean hasInputs()
	{
		return inputs.size() > 0;
	}
	
	/**
	 * Returns if the method returns something.
	 * 
	 * @return True if the method has output, false otherwise.
	 */
	public boolean hasOutput()
	{
		return output != null;
	}
	
	/**
	 * Returns if the method has exceptions.
	 * 
	 * @return True if the method has exceptions, false otherwise.
	 */
	public boolean hasExceptions()
	{
		return exceptions.size() > 0;
	}
	
	/**
	 * Get all the state variables from the inputs and the output.
	 * 
	 * @return The method's state variables.
	 */
	public Set<UpnpStateVariable> getStateVariables()
	{
		Set<UpnpStateVariable> stateVariables = new HashSet<UpnpStateVariable>();
		if (hasInputs())
		{
			stateVariables.addAll(inputs);
		}
		if (hasOutput())
		{
			stateVariables.add(output);
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
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the inputs
	 */
	public Set<UpnpStateVariable> getInputs() {
		return inputs;
	}

	/**
	 * @param inputs the inputs to set
	 */
	public void setInputs(Set<UpnpStateVariable> inputs) {
		this.inputs = inputs;
	}

	/**
	 * @return the output
	 */
	public UpnpStateVariable getOutput() {
		return output;
	}

	/**
	 * @param output the output to set
	 */
	public void setOutput(UpnpStateVariable output) {
		this.output = output;
	}

	/**
	 * @return the exceptions
	 */
	public Set<Class<? extends Exception>> getExceptions() {
		return exceptions;
	}

	/**
	 * @param exceptions the exceptions to set
	 */
	public void setExceptions(Set<Class<? extends Exception>> exceptions) {
		this.exceptions = exceptions;
	}
	
}
