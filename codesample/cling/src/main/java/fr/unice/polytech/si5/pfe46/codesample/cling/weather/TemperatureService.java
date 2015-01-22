package fr.unice.polytech.si5.pfe46.codesample.cling.weather;

import org.apache.commons.io.IOUtils;
import org.fourthline.cling.binding.annotations.*;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

@UpnpService(serviceId = @UpnpServiceId("TemperatureService"),
            serviceType = @UpnpServiceType(value = "TemperatureService", version = 1))
@UpnpStateVariables(
        {
                @UpnpStateVariable(name = "City", datatype = "string", defaultValue = "", sendEvents = false),
                @UpnpStateVariable(name = "CountryCode", datatype = "string", defaultValue = "", sendEvents = false),
                @UpnpStateVariable(name = "Temperature", datatype = "int", defaultValue = "0", sendEvents = false)
        }
)
public class TemperatureService {

    public static final String WEATHER_API_URL = "http://api.openweathermap.org/data/2.5/weather?q=";

    @UpnpAction(out = @UpnpOutputArgument(name = "Temperature"))
    public int getTemperature(@UpnpInputArgument(name = "City") String city,
                                 @UpnpInputArgument(name = "CountryCode") String countryCode)
            throws IOException
    {
        URL weatherURL = new URL(WEATHER_API_URL + city + "," + countryCode);
        JSONObject json = new JSONObject(IOUtils.toString(weatherURL, Charset.forName("UTF-8")));

        Double kelvinTemp = json.getJSONObject("main").getDouble("temp");
        Double celciusTemp = kelvinTemp - 272.15;

        return celciusTemp.intValue();
    }

}
