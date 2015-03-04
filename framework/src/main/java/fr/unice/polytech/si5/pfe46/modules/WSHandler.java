package fr.unice.polytech.si5.pfe46.modules;

import java.util.HashMap;

import org.springframework.web.client.RestTemplate;

public class WSHandler {

    private static WSHandler INSTANCE = new WSHandler();
    private RestTemplate restTemplate = null;

    private WSHandler()
    {
        this.restTemplate = new RestTemplate();
    }

    public static WSHandler getInstance()
    {
        return INSTANCE;
    }

    public String get(String uri)
    {
        String result = restTemplate.getForObject(uri, String.class);
        //JSONObject response = new JSONObject(result);

        return result;
    }

    public String get(String uri, HashMap<String, String> params)
    {
        String result = restTemplate.getForObject(uri, String.class, params);
        //JSONObject response = new JSONObject(result);

        return result;
    }

}
