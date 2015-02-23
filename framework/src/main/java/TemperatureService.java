package fr.unice.polytech.si5.pfe46.generated;

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
        @UpnpStateVariable(name = "city", datatype = "string", defaultValue = "", sendEvents = false),
        @UpnpStateVariable(name = "countryCode", datatype = "string", defaultValue = "", sendEvents = false),
        @UpnpStateVariable(name = "temperature", datatype = "int", defaultValue = "0", sendEvents = false)
    }
)
public class TemperatureService {

    @UpnpAction(out = @UpnpOutputArgument(name = "temperature"))
    public int getTemperature (
            @UpnpInputArgument(name = "city") String city ,        
            @UpnpInputArgument(name = "countryCode") String countryCode         
        )
    throws  IOException  
    {
        URL weatherURL = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + city + "," + countryCode);
        JSONObject json = new JSONObject(IOUtils.toString(weatherURL, Charset.forName("UTF-8")));
        Double kelvinTemp = json.getJSONObject("main").getDouble("temp");
        Double celciusTemp = kelvinTemp - 272.15;
        return celciusTemp.intValue();
    }


}
