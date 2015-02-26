package fr.unice.polytech.si5.pfe46.engine;

import java.util.List;

import fr.unice.polytech.si5.pfe46.engine.inputtype.methods.BluetoothMethodBinding;
import fr.unice.polytech.si5.pfe46.engine.inputtype.methods.MethodBinding;
import fr.unice.polytech.si5.pfe46.engine.inputtype.methods.WsRestMethodBinding;
import fr.unice.polytech.si5.pfe46.engine.inputtype.methods.WsRestVerb;
import fr.unice.polytech.si5.pfe46.engine.inputtype.objects.ConnectedObject;
import fr.unice.polytech.si5.pfe46.engine.inputtype.objects.WsRestObject;

public class MethodContentParser{

    public static MethodContentParser INSTANCE;

    private MethodContentParser()
    {
    }

    public static MethodContentParser getInstance()
    {
    	if (INSTANCE == null)
    	{
    		INSTANCE = new MethodContentParser();
    	}
    	return INSTANCE;
    }

    public /*Pair<*/String/*, Set<Modules>>*/ buildContent(List<MethodBinding> binding)
    {
        StringBuilder res = new StringBuilder();
        //Set<Modules> modules = new HashSet<>();
        
        for (MethodBinding methodBinding : binding)
        {
            ConnectedObject object = methodBinding.getConnectedObject();

            res.append("\nif (objectName.equals(\"");
            res.append(object.getName() + "\")) {");

            if (methodBinding instanceof WsRestMethodBinding)
            {
            	res.append(wsContent((WsRestMethodBinding) methodBinding));
            }
            else if (methodBinding instanceof BluetoothMethodBinding)
            {
            	res.append(bluetoothContent((BluetoothMethodBinding) methodBinding));
            }

            res.append("\n}");
        }
        res.append("return \"{\\\"error\\\":\\\"true\\\"}\";");

        return /*new Pair<String, Set<Modules>>(*/res.toString()/*, modules)*/;
    }

    private String wsContent(WsRestMethodBinding methodBinding)
    {
    	StringBuilder res = new StringBuilder();

        WsRestObject object = (WsRestObject) methodBinding.getConnectedObject();

        res.append("\n\tMap<String, String> params = JsonProcess.jsonToMap(parameters);");

        if (object.isUseOAuth())
        {
        	res.append("\n\tString res = OAuthHandler.getInstance()");

            String provider = object.getProvider();
            String uri = methodBinding.getEndpoint();

            res.append(".callServiceGet(\"");
            res.append(provider);
            res.append("\", \"");
            res.append(uri);
            res.append("\", params.get(\"apiKey\"), params.get(\"apiSecret\"), params.get(\"accessToken\"), params.get(\"secretToken\"), params);");
        }
        else
        {
            if (methodBinding.getVerb() == WsRestVerb.GET)
            {
                res.append("\n\tString res = WSHandler.getInstance().get(uri);");
            }
        }

        res.append("\n\treturn res;");

        return res.toString();

    }

    private String bluetoothContent(BluetoothMethodBinding methodBinding)
    {
    	StringBuilder res = new StringBuilder();
        res.append("\n\tString res = \"\";");
        res.append("\n\treturn res;");

        return res.toString();
    }

}
