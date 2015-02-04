import org.json.JSONObject;

public class Main {

    public static void main(String args[]) {

        String uri = "http://graph.facebook.com/garagesocial";
        JSONObject jo = WSHandler.getInstance().get(uri);
        Resource res = new Resource(jo);

        System.out.println(res);

    }

}
