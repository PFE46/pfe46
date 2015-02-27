package fr.unice.polytech.si5.pfe46.engine;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import fr.unice.polytech.si5.pfe46.engine.inputtype.methods.*;
import fr.unice.polytech.si5.pfe46.engine.inputtype.objects.LibraryObject;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.deser.StdDeserializer;
import org.codehaus.jackson.map.module.SimpleModule;
import org.codehaus.jackson.node.ObjectNode;

import fr.unice.polytech.si5.pfe46.engine.exceptions.JsonParsingException;
import fr.unice.polytech.si5.pfe46.engine.inputtype.Input;
import fr.unice.polytech.si5.pfe46.engine.inputtype.objects.BluetoothObject;
import fr.unice.polytech.si5.pfe46.engine.inputtype.objects.ConnectedObject;
import fr.unice.polytech.si5.pfe46.engine.inputtype.objects.WsRestObject;

/**
 * From JSON string to Input pojo.
 * 
 * @author victorsalle
 */
@SuppressWarnings("deprecation")
public class InputParser {

	private static InputParser INSTANCE;
	private static ObjectMapper mapper;
	
	/* 
	 * Used to differentiate polymorphic types in the input string.
	 *
	 * Those attributes must appear only in the given type among those which are going to be
	 * deserialized ("deviceId" only appears in "BluetoothObject" class, not on the others).
	 *
	 * This is required to be sure that a JSON that contains an attribute "deviceId" will be
	 * deserialized using the "BluetoothObject" class.
	 * 
	 */
	private static final String BLUETOOTH_OBJECT_PROPERTY         = "deviceId";
	private static final String WS_REST_OBJECT_PROPERTY           = "useOAuth";
	private static final String BLUETOOTH_METHOD_BINDING_PROPERTY = "bluetoothMethod";
	private static final String WS_REST_METHOD_BINDING_PROPERTY   = "endpoint";
	private static final String LIBRARY_OBJECT_PROPERTY           = "libraryType";
	private static final String LIBRARY_METHOD_BINDING_PROPERTY   = "methodCode";

	/**
	 * Singleton accessor.
	 * 
	 * @return InputParser instance.
	 */
	public static InputParser getInstance()
	{
		if (INSTANCE == null)
		{
			INSTANCE = new InputParser();
		}
		return INSTANCE;
	}
	
	/**
	 * Private constructor (singleton).
	 */
	private InputParser()
	{
		// Allow deserialization of ConnectedObject subclasses
		MyDeserializer<ConnectedObject> connectedObjectDeserializer = new MyDeserializer<ConnectedObject>(ConnectedObject.class);
		connectedObjectDeserializer.register(BLUETOOTH_OBJECT_PROPERTY, BluetoothObject.class);
		connectedObjectDeserializer.register(WS_REST_OBJECT_PROPERTY, WsRestObject.class);
		connectedObjectDeserializer.register(LIBRARY_OBJECT_PROPERTY, LibraryObject.class);

		// Allow deserialization of MethodBinding subclasses
		MyDeserializer<MethodBinding> methodBindingDeserializer = new MyDeserializer<MethodBinding>(MethodBinding.class);
		methodBindingDeserializer.register(BLUETOOTH_METHOD_BINDING_PROPERTY, BluetoothMethodBinding.class);
		methodBindingDeserializer.register(WS_REST_METHOD_BINDING_PROPERTY, WsRestMethodBinding.class);
		methodBindingDeserializer.register(LIBRARY_METHOD_BINDING_PROPERTY, LibraryMethodBinding.class);

		// Needed to use deserializers above
		SimpleModule module = new SimpleModule("InputDeserializer", new Version(1, 0, 0, null));
		module.addDeserializer(ConnectedObject.class, connectedObjectDeserializer);
		module.addDeserializer(MethodBinding.class, methodBindingDeserializer);

		mapper = new ObjectMapper();  
		mapper.registerModule(module);
	}
	
	/**
	 * From JSON string to Input pojo.
	 * 
	 * @param json JSON String corresponding to an Input object.
	 * @return Input object.
	 * @throws JsonParsingException If an error occurred while parsing.
	 */
	public Input parse(String json) throws JsonParsingException
	{
		Input input;
		
		try
		{
			input = mapper.readValue(json, Input.class);
		}
		catch (IOException e)
		{
            throw new JsonParsingException(e);
		}

        assignConnectedObjectToMethodBindings(input);
		
		return input;
	}
	
	/**
	 * An object MethodBinding has a corresponding ConnectedObject attribute. Unfortunately,
	 * mapping is not done automatically so let's change this attribute manually.
	 * 
	 * @param input Input to edit.
	 */
	private void assignConnectedObjectToMethodBindings(Input input)
	{
		// :'(
		
		// TODO: manage the case where the kind of the method does not match the kind of object,
		// BluetoothMethodBinding with a WsRestObject for example

		for (Method method : input.getMethods())
		{
			for (MethodBinding methodBinding : method.getBindings())
			{
				for (ConnectedObject connectedObject : input.getObjects())
				{
					if (methodBinding.getObject().equals(connectedObject.getName()))
					{
						methodBinding.setConnectedObject(connectedObject);
						break;
					}
				}
			}
		}
	}
	
}	

/**
 * Deserializer considering polymorphism. 
 * http://programmerbruce.blogspot.fr/2011/05/deserialize-json-with-jackson-into.html (visited Feb. 24, 2015)
 * 
 * @author programmerbruce (programmerbruce.blogspot.fr)
 */
@SuppressWarnings("deprecation")
class MyDeserializer<T> extends StdDeserializer<T>
{
	private Map<String, Class<? extends T>> registry;

	MyDeserializer(Class<T> t)
	{
		super(t);
		registry = new HashMap<String, Class<? extends T>>();
	}

	void register(String uniqueAttribute, Class<? extends T> myClass)
	{
		registry.put(uniqueAttribute, myClass);  
	}

	@Override  
	public T deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException
	{
		ObjectMapper mapper = (ObjectMapper) jp.getCodec();
		ObjectNode root = (ObjectNode) mapper.readTree(jp);
		Class<? extends T> myClass = null;
		Iterator<Entry<String, JsonNode>> elementsIterator = root.getFields();
		while (elementsIterator.hasNext())
		{
			Entry<String, JsonNode> element=elementsIterator.next();
			String name = element.getKey();
			if (registry.containsKey(name))
			{
				myClass = registry.get(name);
				break;
			}
		}
		if (myClass == null) return null;
		return mapper.readValue(root, myClass);
	}  
}