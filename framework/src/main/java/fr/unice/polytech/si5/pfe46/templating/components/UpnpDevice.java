package fr.unice.polytech.si5.pfe46.templating.components;

import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a UpnpDevice with its basic information and
 * its services.
 * 
 * @author victorsalle
 */
public class UpnpDevice {

	private String deviceName;
	private String friendlyName;
	private String manufacturerName;
	private Set<UpnpService> services;
	
	//
	// CONSTRUCTOR
	//
	
	public UpnpDevice()
	{
		services = new HashSet<UpnpService>();
	}
	
	//
	// METHOD
	//
	
	public void addService(UpnpService service)
	{
		this.services.add(service);
	}
	
	//
	// GETTERS & SETTERS
	//

	/**
	 * @return the deviceName
	 */
	public String getDeviceName() {
		return deviceName;
	}

	/**
	 * @param deviceName the deviceName to set
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	/**
	 * @return the friendlyName
	 */
	public String getFriendlyName() {
		return friendlyName;
	}

	/**
	 * @param friendlyName the friendlyName to set
	 */
	public void setFriendlyName(String friendlyName) {
		this.friendlyName = friendlyName;
	}

	/**
	 * @return the manufacturerName
	 */
	public String getManufacturerName() {
		return manufacturerName;
	}

	/**
	 * @param manufacturerName the manufacturerName to set
	 */
	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	/**
	 * @return the services
	 */
	public Set<UpnpService> getServices() {
		return services;
	}

	/**
	 * @param services the services to set
	 */
	public void setServices(Set<UpnpService> services) {
		this.services = services;
	}
	
}
