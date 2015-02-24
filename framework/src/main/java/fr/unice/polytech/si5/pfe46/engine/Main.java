package fr.unice.polytech.si5.pfe46.engine;

import fr.unice.polytech.si5.pfe46.engine.exceptions.JsonParsingException;
import fr.unice.polytech.si5.pfe46.engine.inputtype.Input;
import fr.unice.polytech.si5.pfe46.engine.inputtype.methods.BluetoothMethodBinding;
import fr.unice.polytech.si5.pfe46.engine.inputtype.methods.Method;
import fr.unice.polytech.si5.pfe46.engine.inputtype.methods.MethodBinding;
import fr.unice.polytech.si5.pfe46.engine.inputtype.methods.WsRestMethodBinding;
import fr.unice.polytech.si5.pfe46.engine.inputtype.objects.BluetoothObject;
import fr.unice.polytech.si5.pfe46.engine.inputtype.objects.ConnectedObject;
import fr.unice.polytech.si5.pfe46.engine.inputtype.objects.WsRestObject;

public class Main {

	public static void main(String[] args)
	{
		String json = "{\"objects\":[{\"name\":\"WiiBoard\",\"protocol\":\"BLUETOOTH\",\"deviceId\":"
				+ "\"wiiboardid\"},{\"name\":\"SmartBodyAnalyzer\",\"protocol\":\"WS_REST\",\"useOAuth"
				+ "\":true,\"provider\":\"Withings\"}],\"methods\":[{\"name\":\"getWeight\",\"bindings"
				+ "\":[{\"object\":\"WiiBoard\",\"bluetoothMethod\":\"getWiiBoardWeightAddress\"},{"
				+ "\"object\":\"SmartBodyAnalyzer\",\"endpoint\":\"/api/measures\",\"verb\":\"GET\"}]}]}";

		/*
		 *   {
		 *       "objects": [
		 *           {
		 *               "name": "WiiBoard",
		 *               "protocol": "BLUETOOTH",
		 *               "deviceId": "wiiboardid"
		 *           },
		 *           {
		 *               "name": "SmartBodyAnalyzer",
		 *               "protocol": "WS_REST",
		 *               "useOAuth": true,
		 *               "provider": "Withings"
		 *           }
		 *       ],
		 *       "methods": [
		 *           {
		 *               "name": "getWeight",
		 *               "bindings": [
		 *                   {
		 *                       "object": "WiiBoard",
		 *                       "bluetoothMethod": "getWiiBoardWeightAddress"
		 *                   },
		 *                   {
		 *                       "object": "SmartBodyAnalyzer",
		 *                       "endpoint": "/api/measures",
		 *                       "verb": "GET"
		 *                   }
		 *               ]
		 *           }
		 *       ]
		 *   }
		 */

		//
		// Parsing.
		//

		Input input = null;

		try
		{
			input = InputParser.getInstance().parse(json);
		}
		catch (JsonParsingException e)
		{
			System.err.println(e);
		}

		//
		// Println.
		//

		System.out.println(">> OBJECTS\n");
		for (ConnectedObject object : input.getObjects())
		{
			System.out.println(" > name: " + object.getName());
			System.out.println("   protocol: " + object.getProtocol().name());

			if (object instanceof BluetoothObject)
			{
				BluetoothObject bluetoothObject = (BluetoothObject) object;
				System.out.println("   deviceid: " + bluetoothObject.getDeviceId());
			}
			else if (object instanceof WsRestObject)
			{
				WsRestObject wsRestObject = (WsRestObject) object;
				System.out.println("   useOAuth: " + wsRestObject.isUseOAuth());
				System.out.println("   provider: " + wsRestObject.getProvider());
			}
			System.out.println();
		}

		System.out.println(">> METHODS\n");
		for (Method method : input.getMethods())
		{
			System.out.println(" > name: " + method.getName());

			for (MethodBinding methodBinding : method.getBindings())
			{
				System.out.println("   - object: " + methodBinding.getConnectedObject().getName());
				if (methodBinding instanceof BluetoothMethodBinding)
				{
					BluetoothMethodBinding bluetoothMethodBinding = (BluetoothMethodBinding) methodBinding;
					System.out.println("     bluetoothMethod: " + bluetoothMethodBinding.getBluetoothMethod());
				}
				else if (methodBinding instanceof WsRestMethodBinding)
				{
					WsRestMethodBinding wsRestMethodBinding = (WsRestMethodBinding) methodBinding;
					System.out.println("     verb: " + wsRestMethodBinding.getVerb().name());
					System.out.println("     endpoint: " + wsRestMethodBinding.getEndpoint());
				}
			}
			System.out.println();
		}
	}

}
