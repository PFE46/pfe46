package fr.unice.polytech.si5.pfe46.modules.OAuth;

import fr.unice.polytech.si5.pfe46.modules.OAuth.API.WithingsApi;
import fr.unice.polytech.si5.pfe46.modules.OAuth.exceptions.NoSuchProviderException;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;

import java.util.HashMap;
import java.util.Map;

public class OAuthHandler {

	private static OAuthHandler INSTANCE = new OAuthHandler();

	private static Map<String, Class<? extends DefaultApi10a>> providers;
	static
	{
		providers = new HashMap<>();
		providers.put("Withings", WithingsApi.class);
	}

	public static OAuthHandler getInstance()
	{
		return INSTANCE;
	}

	public String callServiceGet(String provider, String uri, String apiKey, String apiSecret,
								 String accessToken, String secretToken, Map<String, String> params)
								 throws NoSuchProviderException
	{
		return callService(provider, uri, Verb.GET, apiKey, apiSecret, accessToken, secretToken, params);
	}

	public String callServicePost(String provider, String uri, String apiKey, String apiSecret,
								  String accessToken, String secretToken, Map<String, String> params)
								  throws NoSuchProviderException
	{
		return callService(provider, uri, Verb.POST, apiKey, apiSecret, accessToken, secretToken, params);
	}

	private String callService(String provider, String uri, Verb verb, String apiKey, String apiSecret,
							   String accessToken, String secretToken, Map<String, String> params)
							   throws NoSuchProviderException
	{
		Class<? extends DefaultApi10a> serviceProvider;

		try
		{
			serviceProvider = providers.get(provider);
		}
		catch (NullPointerException e)
		{
			throw new NoSuchProviderException(provider);
		}

		OAuthService service = new ServiceBuilder()
									.provider(serviceProvider)
									.apiKey(apiKey)
									.apiSecret(apiSecret)
									.signatureType(SignatureType.QueryString)
									.build();

		Token aT = new Token(accessToken, secretToken);

		OAuthRequest request = new OAuthRequest(verb, uri);

		for (Map.Entry<String, String> e : params.entrySet()) {
			request.addQuerystringParameter(e.getKey(), e.getValue());
		}

		service.signRequest(aT, request);
		Response response = request.send();

		return response.getBody();

	}

}
