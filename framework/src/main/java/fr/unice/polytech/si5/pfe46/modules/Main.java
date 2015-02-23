package fr.unice.polytech.si5.pfe46.modules;

import fr.unice.polytech.si5.pfe46.modules.OAuth.OAuthHandler;
import fr.unice.polytech.si5.pfe46.modules.OAuth.exceptions.NoSuchProviderException;

import java.util.HashMap;

public class Main {

    public static void main(String args[]) {

//        String uri = "http://graph.facebook.com/haitaar";
//        JSONObject jo = WSHandler.getInstance().get(uri);
//        Resource res = new Resource(jo);
//
//        System.out.println(res);
//        System.out.println(res.getAttribute("locale"));

        /* Withings WS call */

        String uri = "https://wbsapi.withings.net/measure?action=getmeas";
        String apiKey = "03909c3fd264295cbbce7121c877dd0c2feb302f0c7e79f7240b0c9d8bfe";
        String apiSecret = "7217b7ffc0b0d9599d3ec34d16b2be575b46789dbf0e8c7ce050611e248ff";

        String accessToken = "01686f061b6154ed55912f6d207bb0217ee224d959f0332ed055507d54d64c6";
        String secretToken = "0df0e4f38b1be7582b545e70d129e8b371616e03e7424ffbf95d10f77db4e";

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userId", "5095987");

        try
        {
        	String res = OAuthHandler.getInstance().callServiceGet("Withings", uri, apiKey, apiSecret, accessToken, secretToken, params);
        	System.out.println(res);
        }
        catch (NoSuchProviderException e)
        {
        	System.err.println(e);
        }

    }

}
