package fr.unice.polytech.si5.pfe46.engine.inputtype;

import java.util.List;

import fr.unice.polytech.si5.pfe46.engine.inputtype.methods.Method;
import fr.unice.polytech.si5.pfe46.engine.inputtype.objects.ConnectedObject;

public class Input {

	private List<ConnectedObject> objects;
	private List<Method> methods;
	
	public List<ConnectedObject> getObjects() { return objects; }
	public void setObjects(List<ConnectedObject> objects) { this.objects = objects; }
	
	public List<Method> getMethods() { return methods; }
	public void setMethods(List<Method> methods) { this.methods = methods; }
	
}
