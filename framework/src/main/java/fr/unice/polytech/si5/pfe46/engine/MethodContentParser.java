package fr.unice.polytech.si5.pfe46.engine;

public class MethodContentParser {

    public static MethodContentParser INSTANCE = new MethodContentParser();

    private MethodContentParser() {}

    public static MethodContentParser getInstance() { return INSTANCE; }

    public String buildContent() {

        String res = "";

        // foreach (object)
        res += "if (objectName == object.name) {";

        // if(object.protocol == "REST")
        res += wsContent();

        // else if (object.protocol == bluetooth)

        res += "\n}";

        return res;

    }

    private String wsContent() {

        String res = "";

        // if (object.oauth == true)
        res += "\n\tString res = OAuthHandler.getInstance().callServiceGet(provider, uri, apiKey, apiSecret, accessToken, secretToken, params);";

        // else
        res += "\n\tString res = WSHandler.getInstance().get(uri);";

        res += "\n\treturn res;";

        return res;

    }

    private String bluetoothContent() {
        return "";
    }

}
