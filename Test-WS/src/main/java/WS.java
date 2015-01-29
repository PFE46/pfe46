import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.Set;

public class WS {

    public static void main(String args[]) {
        RestTemplate restTemplate = new RestTemplate();
        //Page page = restTemplate.getForObject("http://graph.facebook.com/pivotalsoftware", Page.class);
        String result = restTemplate.getForObject("http://graph.facebook.com/pivotalsoftware", String.class);

        //System.out.println(result);

        JSONObject jo = new JSONObject(result);
        Resource res = new Resource(jo);

        System.out.println(res);

//        for (int i = 0 ; i < jo.names().length() ; i++) {
//            System.out.println(jo.get((String) jo.names().get(i)).getClass());
//        }

    }

}
