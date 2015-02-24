package fr.unice.polytech.si5.pfe46.engine;

import fr.unice.polytech.si5.pfe46.engine.inputtype.methods.BluetoothMethodBinding;
import fr.unice.polytech.si5.pfe46.engine.inputtype.methods.MethodBinding;
import fr.unice.polytech.si5.pfe46.engine.inputtype.methods.WsRestMethodBinding;
import fr.unice.polytech.si5.pfe46.engine.inputtype.methods.WsRestVerb;
import fr.unice.polytech.si5.pfe46.engine.inputtype.objects.ConnectedObject;
import fr.unice.polytech.si5.pfe46.engine.inputtype.objects.WsRestObject;

import java.util.List;

public class MethodContentParser {

    public static MethodContentParser INSTANCE = new MethodContentParser();

    private MethodContentParser() {}

    public static MethodContentParser getInstance() { return INSTANCE; }

    public String buildContent(List<MethodBinding> binding) {

        String res = "";

        for (MethodBinding methodBinding : binding) {

            ConnectedObject object = methodBinding.getConnectedObject();

            res += "\nif (objectName.equals(\"" + object.getName() + "\")) {";

            if (methodBinding instanceof WsRestMethodBinding) {
                res += wsContent((WsRestMethodBinding) methodBinding);
            }
            else if (methodBinding instanceof BluetoothMethodBinding) {
                res += bluetoothContent((BluetoothMethodBinding) methodBinding);
            }

            res += "\n}";

        }

        return res;

    }

    private String wsContent(WsRestMethodBinding methodBinding) {

        String res = "";

        WsRestObject object = (WsRestObject) methodBinding.getConnectedObject();

        res += "\n\tHashMap<String, String> params = JsonProcess.jsonToMap(parameters);";

        if (object.isUseOAuth()) {
            res += "\n\tString res = OAuthHandler.getInstance()";

            String provider = object.getProvider();
            String uri = methodBinding.getEndpoint();

            res += ".callServiceGet(\"" + provider + "\", \"" + uri + "\", apiKey, apiSecret, accessToken, secretToken, params);";
        }
        else {
            if (methodBinding.getVerb() == WsRestVerb.GET) {
                res += "\n\tString res = WSHandler.getInstance().get(uri);";
            }
        }

        res += "\n\treturn res;";

        return res;

    }

    private String bluetoothContent(BluetoothMethodBinding methodBinding) {

        String res = "";
        res += "\n\tString res = \"\";";
        res += "\n\treturn res;";

        return res;
    }

}
