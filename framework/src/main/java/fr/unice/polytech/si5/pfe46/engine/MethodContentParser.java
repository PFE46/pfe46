package fr.unice.polytech.si5.pfe46.engine;

import java.util.List;

import fr.unice.polytech.si5.pfe46.engine.inputtype.methods.*;
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

    public String buildContent(List<MethodBinding> binding)
    {
        StringBuilder res = new StringBuilder();

        boolean first = true;
        
        for (MethodBinding methodBinding : binding)
        {
            ConnectedObject object = methodBinding.getConnectedObject();

            if (first)
            {
            	res.append("\n\tif");
            }
            else
            {
            	res.append("\n\telse if");
            }
            res.append(" (objectName.equals(\"");
            res.append(object.getName() + "\")) {");

            if (methodBinding instanceof WsRestMethodBinding)
            {
            	res.append(wsContent((WsRestMethodBinding) methodBinding));
            }
            else if (methodBinding instanceof BluetoothMethodBinding)
            {
            	res.append(bluetoothContent((BluetoothMethodBinding) methodBinding));
            }
            else if (methodBinding instanceof LibraryMethodBinding)
            {
                res.append(libraryContent((LibraryMethodBinding) methodBinding));
            }

            res.append("\n\t}");
            
            first = false;
        }
        res.append("return \"{\\\"error\\\":\\\"true\\\"}\";");

        return res.toString();
    }

    private String wsContent(WsRestMethodBinding methodBinding)
    {
    	StringBuilder res = new StringBuilder();

        WsRestObject object = (WsRestObject) methodBinding.getConnectedObject();

        res.append("\n\t\tMap<String, String> params = JsonProcess.jsonToMap(parameters);");

        if (object.isUseOAuth())
        {
        	res.append("\n\t\tString res = OAuthHandler.getInstance()");

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
            if (methodBinding.getVerb() == WsRestMethodBinding.WsRestVerb.GET)
            {
                res.append("\n\t\tString res = WSHandler.getInstance().get(uri);");
            }
        }

        res.append("\n\t\treturn res;");

        return res.toString();
    }

    private String bluetoothContent(BluetoothMethodBinding methodBinding)
    {
    	StringBuilder res = new StringBuilder();
        res.append("\n\t\tString res = \"\";");
        res.append("\n\t\treturn res;");

        return res.toString();
    }

    private String libraryContent(LibraryMethodBinding methodBinding)
    {
        return "\n\t\t" + methodBinding.getMethodCode();
    }

}
