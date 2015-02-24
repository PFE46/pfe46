package fr.unice.polytech.si5.pfe46.utils;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JsonProcess {

    public static Map<String, String> jsonToMap(String t) throws JSONException {

        //TODO : Handle exception when malformed json string
        Map<String, String> map = new HashMap<>();

        JSONObject jsonObject = new JSONObject(t);

        Iterator it = jsonObject.keys();

        while (it.hasNext()) {

            String key = (String) it.next();
            String value = jsonObject.getString(key);
            map.put(key, value);

        }

        return map;
    }

}
