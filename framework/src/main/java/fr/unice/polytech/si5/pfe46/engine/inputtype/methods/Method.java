package fr.unice.polytech.si5.pfe46.engine.inputtype.methods;

import java.util.List;

public class Method {

	private String name;
	private List<MethodBinding> bindings;
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public List<MethodBinding> getBindings() { return bindings; }
	public void setBindings(List<MethodBinding> bindings) { this.bindings = bindings; }
	
}
