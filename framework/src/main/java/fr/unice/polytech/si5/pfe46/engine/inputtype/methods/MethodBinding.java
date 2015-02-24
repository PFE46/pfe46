package fr.unice.polytech.si5.pfe46.engine.inputtype.methods;

import fr.unice.polytech.si5.pfe46.engine.inputtype.objects.ConnectedObject;

public class MethodBinding {
	
	private String object; // TODO: link to ConnectedObject
	private ConnectedObject connectedObject;
	
	public String getObject() { return object; }
	public void setObject(String object) { this.object = object; }
	
	public ConnectedObject getConnectedObject() { return connectedObject; }
	public void setConnectedObject(ConnectedObject connectedObject) { this.connectedObject = connectedObject; }
	
}
