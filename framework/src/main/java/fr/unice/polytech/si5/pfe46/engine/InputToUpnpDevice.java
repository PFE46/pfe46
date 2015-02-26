package fr.unice.polytech.si5.pfe46.engine;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.si5.pfe46.engine.inputtype.Input;
import fr.unice.polytech.si5.pfe46.engine.inputtype.methods.Method;
import fr.unice.polytech.si5.pfe46.engine.inputtype.methods.MethodBinding;
import fr.unice.polytech.si5.pfe46.templating.components.UpnpDevice;
import fr.unice.polytech.si5.pfe46.templating.components.UpnpMethod;
import fr.unice.polytech.si5.pfe46.templating.components.UpnpService;
import fr.unice.polytech.si5.pfe46.templating.components.UpnpStateVariable;
import fr.unice.polytech.si5.pfe46.templating.components.UpnpStateVariableType;
import fr.unice.polytech.si5.pfe46.templating.exceptions.DuplicateMethodSignatureException;
import fr.unice.polytech.si5.pfe46.templating.exceptions.UpnpStateVariableConflictException;

/**
 * From Input pojo to UpnpDevice.
 * 
 * @author victorsalle
 */
public class InputToUpnpDevice {

	private static InputToUpnpDevice INSTANCE;
	
	/**
	 * Singleton accessor.
	 * 
	 * @return InputToUpnpDevice instance.
	 */
	public static InputToUpnpDevice getInstance()
	{
		if (INSTANCE == null)
		{
			INSTANCE = new InputToUpnpDevice();
		}
		return INSTANCE;
	}
	
	/**
	 * Private constructor (singleton).
	 */
	private InputToUpnpDevice()
	{
	}
	
	/**
	 * Transform an Input pojo in a UpnpDevice.
	 * 
	 * @param input Input to transform.
	 * @return Corresponding UpnpDevice.
	 * @throws UpnpStateVariableConflictException 
	 * @throws DuplicateMethodSignatureException 
	 */
	public UpnpDevice getDevice(Input input) throws UpnpStateVariableConflictException, DuplicateMethodSignatureException
	{
		UpnpDevice device = createDevice(input);
		List<UpnpMethod> methods = createMethods(input);
		UpnpService service = createService();
		
		for (UpnpMethod method : methods)
		{
			service.addMethod(method);
		}
		
		device.addService(service);
		
		return device;
	}
	
	private List<UpnpMethod> createMethods(Input input) throws UpnpStateVariableConflictException
	{
		List<UpnpMethod> methods = new ArrayList<>();
		
		for (Method inputMethod : input.getMethods())
		{
			UpnpMethod upnpMethod = new UpnpMethod();
			
			// Name
			upnpMethod.setName(inputMethod.getName());
			
			// Exception // TODO
			// upnpMethod.addException(IOException.class);
	        
	        // Input (parameters)
	        UpnpStateVariable jsonInputMethodParameter = new UpnpStateVariable();
	        jsonInputMethodParameter.setName("parameters");
	        jsonInputMethodParameter.setDatatype(UpnpStateVariableType.STRING);
	        
	        UpnpStateVariable objectName = new UpnpStateVariable();
	        objectName.setName("objectName");
	        objectName.setDatatype(UpnpStateVariableType.STRING);
	        
	        upnpMethod.addInput(jsonInputMethodParameter);
	        upnpMethod.addInput(objectName);
	        
	        // Output
	        UpnpStateVariable jsonOutput = new UpnpStateVariable();
	        jsonOutput.setName("jsonOutput");
	        jsonOutput.setDatatype(UpnpStateVariableType.STRING);
	        
	        upnpMethod.setOutput(jsonOutput);
	        
	        // Content
	        List<MethodBinding> methodBindings = new ArrayList<>();
	        for (Method m : input.getMethods())
	        {
	        	methodBindings.addAll(m.getBindings());
	        }
	        
	        upnpMethod.setContent(MethodContentParser.getInstance().buildContent(methodBindings));
	        
	        methods.add(upnpMethod);
		}
		
		return methods;
	}
	
	private UpnpService createService()
	{
		UpnpService upnpService = new UpnpService();
		upnpService.setName("Service"); // do not name UpnpService (conflict name)
		
		return upnpService;
	}
	
	private UpnpDevice createDevice(Input input)
	{
		UpnpDevice device = new UpnpDevice();
		device.setDeviceName("Device"); // TODO
        device.setFriendlyName("Device");
        device.setManufacturerName("PFE46");
		return device;
	}
	
}
