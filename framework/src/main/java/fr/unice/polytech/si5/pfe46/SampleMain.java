package fr.unice.polytech.si5.pfe46;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import fr.unice.polytech.si5.pfe46.templating.components.UpnpDevice;
import fr.unice.polytech.si5.pfe46.templating.components.UpnpMethod;
import fr.unice.polytech.si5.pfe46.templating.components.UpnpService;
import fr.unice.polytech.si5.pfe46.templating.components.UpnpStateVariable;
import fr.unice.polytech.si5.pfe46.templating.components.UpnpStateVariableType;
import fr.unice.polytech.si5.pfe46.templating.engine.MavenProjectGenerator;
import fr.unice.polytech.si5.pfe46.templating.exceptions.DuplicateMethodSignatureException;
import fr.unice.polytech.si5.pfe46.templating.exceptions.UpnpStateVariableConflictException;

public class SampleMain 
{
    public static void main( String[] args ) throws IOException
    {
    	/*********************
    	 * 
    	 * Create a UpnpMethod
    	 * 
    	 *********************/
    	
        UpnpMethod getTemperature = new UpnpMethod();
        
        // Name
        getTemperature.setName("getTemperature");
        
        // Exception
        getTemperature.addException(IOException.class);
        
        // Input (parameters)
        UpnpStateVariable city = new UpnpStateVariable();
        city.setName("city");
        city.setDatatype(UpnpStateVariableType.STRING);
        
        UpnpStateVariable countryCode = new UpnpStateVariable();
        countryCode.setName("countryCode");
        countryCode.setDatatype(UpnpStateVariableType.STRING);
        
        try
        {
        	getTemperature.addInput(city);
        	getTemperature.addInput(countryCode);
        }
        catch (UpnpStateVariableConflictException e)
        {
        	System.err.println(e);
        }
        
        // Output (return)
        UpnpStateVariable temperature = new UpnpStateVariable();
        temperature.setName("temperature");
        temperature.setDatatype(UpnpStateVariableType.INT);
        
        getTemperature.setOutput(temperature);
        
        // Content
        getTemperature.setContent(
        			"        " + "URL weatherURL = new URL(\"http://api.openweathermap.org/data/2.5/weather?q=\" + city + \",\" + countryCode);"
                + "\n        " + "JSONObject json = new JSONObject(IOUtils.toString(weatherURL, Charset.forName(\"UTF-8\")));"
                + "\n        " + "Double kelvinTemp = json.getJSONObject(\"main\").getDouble(\"temp\");"
                + "\n        " + "Double celciusTemp = kelvinTemp - 272.15;"
                + "\n        " + "return celciusTemp.intValue();"
        );
        

    	/*********************************
    	 * 
    	 * Create a UpnpService
    	 *   and
    	 * Bind the previous method to it
    	 * 
    	 *********************************/
        
        UpnpService weatherService = new UpnpService();
        weatherService.setName("TemperatureService");
        
        try
        {
        	weatherService.addMethod(getTemperature);
        }
        catch (UpnpStateVariableConflictException | DuplicateMethodSignatureException e)
        {
        	System.err.println(e);
        }
        
        /*********************************
         * 
         * Create a UpnpDevice
         *   and
         * Bind the previous service to it
         * 
         **********************************/
        
        UpnpDevice device = new UpnpDevice();
        device.setDeviceName("Weather");
        device.setFriendlyName("Weather Station");
        device.setManufacturerName("PFE46");
        
        Set<UpnpService> services = new HashSet<UpnpService>();
        services.add(weatherService);
        device.setServices(services);
        
        /******************
         * 
         * Code generation
         * 
         ******************/
        
        MavenProjectGenerator g = new MavenProjectGenerator();
        try {
        	g.generateMavenProject(device);
        } catch (Exception e)
        {
        	e.printStackTrace();
        }
    }
}
