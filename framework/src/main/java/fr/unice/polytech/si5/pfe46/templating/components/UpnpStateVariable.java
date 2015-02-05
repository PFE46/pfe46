package fr.unice.polytech.si5.pfe46.templating.components;

import java.util.Collection;

import fr.unice.polytech.si5.pfe46.utils.Pair;

/**
 * UpnpStateVariable class. A UpnpStateVariable is needed for each method's input/output. 
 * 
 * @author victorsalle
 */
public class UpnpStateVariable {

	private String name;
	private Boolean sendEvents;
	private UpnpStateVariableType datatype;
	
	//
	// CONSTRUCTORS
	//
	
	/**
	 * Empty constructor.
	 */
	public UpnpStateVariable()
	{
		this.sendEvents = Boolean.FALSE;
	}
	
	/**
	 * Constructor of a state variable.
	 * 
	 * @param name The name of the state variable.
	 * @param sendEvents True if the variable must send events, false otherwise.
	 * @param datatype The type of the state variable.
	 */
	public UpnpStateVariable(String name, Boolean sendEvents, UpnpStateVariableType datatype)
	{
		this();
		this.name = name;
		this.datatype = datatype;
		if (this.sendEvents != null)
		{
			this.sendEvents = sendEvents;
		}
	}
	
	/**
	 * Constructor of a state variable that doesn't send events.
	 * 
	 * @param name The name of the state variable.
	 * @param datatype The type of the state variable.
	 */
	public UpnpStateVariable(String name, UpnpStateVariableType datatype)
	{
		this(name, Boolean.FALSE, datatype);
	}
	
	//
	// METHOD
	//
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		return "{name=" + name + ",sendEvents=" + sendEvents + ",datatype=" + datatype.clingType + "}";
	}
	
	//
	// STATIC METHODS
	//
	
	/**
	 * Check if there is a conflict between the first and the second variable.
	 * 
	 * @param first First UpnpStateVariable to check.
	 * @param second Second UpnpStateVariable to check.
	 * @return False if there is a conflict, true otherwise.
	 */
	public static boolean checkNoConflict(UpnpStateVariable first, UpnpStateVariable second)
	{
		if (first.name.equals(second.name))
		{
			return (first.sendEvents == second.sendEvents && first.datatype == second.datatype);
		}
		
		return true;
	}
	
	/**
	 * Check if there is a conflict between the variable and one of the collection.
	 * 
	 * @param variable UpnpStateVariable to check.
	 * @param collectionOfVariables Collection of UpnpStateVariable to check.
	 * @return The UpnpStateVariable from the collection that conflicts with the first parameter, null otherwise.
	 */
	public static UpnpStateVariable checkNoConflict(UpnpStateVariable variable, Collection<UpnpStateVariable> collectionOfVariables)
	{
		for (UpnpStateVariable variableFromList : collectionOfVariables)
		{
			if (!checkNoConflict(variable, variableFromList))
			{
				return variableFromList;
			}
		}
		
		return null;
	}
	
	/**
	 * Check if there is a conflict between one of the variable from the first collection and one of the second.
	 * 
	 * @param first First collection of UpnpStateVariable to check.
	 * @param second Second collection of UpnpStateVariable to check.
	 * @return A pair containing the two UpnpStateVariable that conflicts, null otherwise.
	 */
	public static Pair<UpnpStateVariable, UpnpStateVariable> checkNoConflict(Collection<UpnpStateVariable> first, Collection<UpnpStateVariable> second)
	{
		for (UpnpStateVariable variable1 : first)
		{
			for (UpnpStateVariable variable2 : second)
			{
				if (!checkNoConflict(variable1, variable2))
				{
					return new Pair<UpnpStateVariable, UpnpStateVariable>(variable1, variable2);
				}
			}
		}
		return null;
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
	 * @return the sendEvents
	 */
	public Boolean getSendEvents() {
		return sendEvents;
	}

	/**
	 * @param sendEvents the sendEvents to set
	 */
	public void setSendEvents(Boolean sendEvents) {
		this.sendEvents = sendEvents;
	}

	/**
	 * @return the datatype
	 */
	public UpnpStateVariableType getDatatype() {
		return datatype;
	}

	/**
	 * @param datatype the datatype to set
	 */
	public void setDatatype(UpnpStateVariableType datatype) {
		this.datatype = datatype;
	}
	
}
