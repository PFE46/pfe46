package fr.unice.polytech.si5.pfe46.Modules.OAuth;

import fr.unice.polytech.si5.pfe46.Modules.OAuth.API.WithingsApi;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;

import java.util.HashMap;
import java.util.Map;

public class OAuthHandler {

    private static OAuthHandler INSTANCE = new OAuthHandler();

    public OAuthHandler() {
    }

    public static OAuthHandler getInstance() {
        return INSTANCE;
    }

    public String callWithingsService(String uri, String apiKey, String apiSecret,
                                    String accessToken, String secretToken, HashMap<String, String> params) {

        OAuthService service = new ServiceBuilder()
                .provider(WithingsApi.class)
                .apiKey(apiKey)
                .apiSecret(apiSecret)
                .signatureType(SignatureType.QueryString)
                .build();

        Token aT = new Token(accessToken, secretToken);

        OAuthRequest request = new OAuthRequest(Verb.GET, uri);

        for (Map.Entry<String, String> e : params.entrySet()) {
            request.addQuerystringParameter(e.getKey(), e.getValue());
        }

        service.signRequest(aT, request);
        Response response = request.send();

        return response.getBody();

    }

}
