package fr.unice.polytech.si5.pfe46.modules;

import java.util.List;

import fr.unice.polytech.si5.pfe46.engine.InputParser;
import fr.unice.polytech.si5.pfe46.engine.MethodContentParser;
import fr.unice.polytech.si5.pfe46.engine.exceptions.JsonParsingException;
import fr.unice.polytech.si5.pfe46.engine.inputtype.Input;
import fr.unice.polytech.si5.pfe46.engine.inputtype.methods.Method;
import fr.unice.polytech.si5.pfe46.engine.inputtype.methods.MethodBinding;

public class MainModule {

    public /*static*/ void main(String args[]) {

//        String uri = "http://graph.facebook.com/haitaar";
//        JSONObject jo = WSHandler.getInstance().get(uri);
//        Resource res = new Resource(jo);
//
//        System.out.println(res);
//        System.out.println(res.getAttribute("locale"));

        /* Withings WS call */

//        String uri = "https://wbsapi.withings.net/measure?action=getmeas";
//        String apiKey = "03909c3fd264295cbbce7121c877dd0c2feb302f0c7e79f7240b0c9d8bfe";
//        String apiSecret = "7217b7ffc0b0d9599d3ec34d16b2be575b46789dbf0e8c7ce050611e248ff";
//
//        String accessToken = "01686f061b6154ed55912f6d207bb0217ee224d959f0332ed055507d54d64c6";
//        String secretToken = "0df0e4f38b1be7582b545e70d129e8b371616e03e7424ffbf95d10f77db4e";
//
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("userId", "5095987");
//        params.put("meastype", "1"); // 1 : indice poids
//
//        try
//        {
//        	String res = OAuthHandler.getInstance().callServiceGet("Withings", uri, apiKey, apiSecret, accessToken, secretToken, params);
//        	System.out.println(res);
//        }
//        catch (NoSuchProviderException e)
//        {
//        	System.err.println(e);
//        }

        /* Method Content Parser */

        String json = "{\"objects\":[{\"name\":\"WiiBoard\",\"protocol\":\"LIBRARY\",\"libraryType\":"
                + "\"JAR\", \"id\": \"WiiRemoteJ\"},{\"name\":\"SmartBodyAnalyzer\",\"protocol\":\"WS_REST\",\"useOAuth"
                + "\":true,\"provider\":\"Withings\"}],\"methods\":[{\"name\":\"getWeight\",\"bindings"
                + "\":[{\"object\":\"WiiBoard\", \"methodCode\":\"BBImpl bbimpl = new BBImpl(); \\n\\t\\tbbimpl.getWeight();\", \"imports\": [\"ImportClass\"]},{"
                + "\"object\":\"SmartBodyAnalyzer\",\"endpoint\":\"https://wbsapi.withings.net/measure?action=getmeas&meastype=1\",\"verb\":\"GET\"}]}]}";

        Input input = null;

        try
        {
            input = InputParser.getInstance().parse(json);
        }
        catch (JsonParsingException e)
        {
            System.err.println(e);
        }

        if (input != null)
        {
            for (Method method : input.getMethods())
            {
                List<MethodBinding> binding = method.getBindings();

                String res = MethodContentParser.getInstance().buildContent(binding);
                System.out.println(res);
            }

        }

    }

}
