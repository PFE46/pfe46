package fr.unice.polytech.si5.pfe46.templating.components;

/**
 * Enumeration of available types that are compatible with Cling library
 * for a UpnpStateVariable.
 * 
 * @author victorsalle
 */
public enum UpnpStateVariableType {

	// TODO: add types
	
	BOOLEAN("boolean", "boolean", "0"),
	INT("int", "int", "0"),
	STRING("String", "string", "");
	
	String javaType;
	String clingType;
	String defaultValue;
	
	/**
	 * Constructor of a type.
	 * @param javaType Java type.
	 * @param clingType Cling compatible type.
	 * @param defaultValue The default value.
	 */
	UpnpStateVariableType(String javaType, String clingType, String defaultValue)
	{
		this.javaType = javaType;
		this.clingType = clingType;
		this.defaultValue = defaultValue;
	}

	/**
	 * @return the javaType
	 */
	public String getJavaType() {
		return javaType;
	}

	/**
	 * @param javaType the javaType to set
	 */
	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	/**
	 * @return the clingType
	 */
	public String getClingType() {
		return clingType;
	}

	/**
	 * @param clingType the clingType to set
	 */
	public void setClingType(String clingType) {
		this.clingType = clingType;
	}

	/**
	 * @return the defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @param defaultValue the defaultValue to set
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
}
