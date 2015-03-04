package Modules;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class Resource {

    private Map<String, Object> attributes;

    public Resource() {
        attributes = new HashMap<String, Object>();
    }

    public Resource(JSONObject jsonObject) {
        initFromJson(jsonObject);
    }

    public Resource(String response) {
        JSONObject jsonObject = new JSONObject(response);
        initFromJson(jsonObject);
    }

    public Resource(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    private void initFromJson(JSONObject jsonObject) {

        this.attributes = new HashMap<String, Object>();

        Set keys = jsonObject.keySet();
        Iterator it = keys.iterator();

        while (it.hasNext()) {

            String key = (String) it.next();
            // loop to get the dynamic key
            Object value = jsonObject.get(key);

            if (value instanceof JSONObject) {
                this.addAttribute(key, new Resource((JSONObject) value));
            } else if (value instanceof JSONArray) {

                JSONArray array = (JSONArray) value;
                List<Resource> ress = new ArrayList<Resource>();

                for (int i = 0; i < array.length(); i++) {
                    ress.add(new Resource(array.getJSONObject(i)));
                }
                this.addAttribute(key, ress);

            } else {
                this.addAttribute(key, value);
            }

        }

    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public void addAttribute(String key, Object value) {
        this.attributes.put(key, value);
    }

    public Object getAttribute(String key) {
        return this.attributes.get(key);
    }

    public String toString() {

        String res = "";

        for (Map.Entry<String, Object> e : this.attributes.entrySet()) {

            String begin = (e.getValue() instanceof Resource) ? "{\n" : "";
            String end = (e.getValue() instanceof Resource) ? "}" : "";

            res += e.getKey() + " : " + begin + e.getValue() + end + "\n";
        }

        return res;

    }

}
