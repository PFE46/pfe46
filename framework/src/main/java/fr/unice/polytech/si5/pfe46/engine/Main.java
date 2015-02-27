package fr.unice.polytech.si5.pfe46.engine;

import java.io.IOException;

import fr.unice.polytech.si5.pfe46.engine.exceptions.JsonParsingException;
import fr.unice.polytech.si5.pfe46.engine.inputtype.Input;
import fr.unice.polytech.si5.pfe46.templating.components.UpnpDevice;
import fr.unice.polytech.si5.pfe46.templating.exceptions.DuplicateMethodSignatureException;
import fr.unice.polytech.si5.pfe46.templating.exceptions.UpnpStateVariableConflictException;

public class Main {

	public static void main(String[] args)
	{
        String json = "{\"objects\":[{\"name\":\"WiiBoard\",\"protocol\":\"LIBRARY\",\"libraryType\":"
                + "\"JAR\", \"id\": \"WiiRemoteJ\"},{\"name\":\"SmartBodyAnalyzer\",\"protocol\":\"WS_REST\",\"useOAuth"
                + "\":true,\"provider\":\"Withings\"}],\"methods\":[{\"name\":\"getWeight\",\"bindings"
                + "\":[{\"object\":\"WiiBoard\", \"methodCode\":\"//getWiiBoardWeightAddress();\", \"imports\": [\"ImportClass\"]},{"
                + "\"object\":\"SmartBodyAnalyzer\",\"endpoint\":\"https://wbsapi.withings.net/measure?action=getmeas&meastype=1\",\"verb\":\"GET\"}]}]}";


		/*
		 *   {
		 *       "objects": [
		 *           {
		 *               "name": "Pulse O2",
		 *               "protocol": "BLUETOOTH",
		 *               "deviceId": "Pulse O2"
		 *           },
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
		 *                       "methodCode": "getWiiBoardWeightAddress();",
		 *                       "imports": ["ImportClass"]
		 *                   },
		 *                   {
		 *                       "object": "SmartBodyAnalyzer",
		 *                       "endpoint": "https://wbsapi.withings.net/measure?action=getmeas",
		 *                       "verb": "GET"
		 *                   }
		 *               ]
		 *           }
		 *       ]
		 *   }
		 */
        try
		{
			// Parsing
			Input input = InputParser.getInstance().parse(json);
			
			// Generate UpnpDevice
			UpnpDevice upnpDevice = InputToUpnpDevice.getInstance().getDevice(input);
			
			// Generate Maven project
			MavenProjectGenerator.getInstance().generateMavenProject(upnpDevice);
		}
		catch (JsonParsingException | IOException
				| UpnpStateVariableConflictException | DuplicateMethodSignatureException e)
		{
			e.printStackTrace();
		}
		
/*
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
		}*/
	}

}
