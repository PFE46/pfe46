package fr.unice.polytech.si5.pfe46.codesample.cling.weather;

import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.binding.annotations.AnnotationLocalServiceBinder;
import org.fourthline.cling.model.DefaultServiceManager;
import org.fourthline.cling.model.ValidationException;
import org.fourthline.cling.model.meta.*;
import org.fourthline.cling.model.types.DeviceType;
import org.fourthline.cling.model.types.UDADeviceType;
import org.fourthline.cling.model.types.UDN;

public class WeatherServer implements Runnable {

    public static void main(String[] args)
    {
        Thread serverThread = new Thread(new WeatherServer());
        serverThread.setDaemon(false);
        serverThread.start();
    }

    public LocalDevice createWeatherDevice() throws ValidationException
    {
        DeviceIdentity identity = new DeviceIdentity(UDN.uniqueSystemIdentifier("Weather"));
        DeviceType type = new UDADeviceType("Weather", 1);
        DeviceDetails details = new DeviceDetails("Weather Station",
                                                    new ManufacturerDetails("PFE46"));

        LocalService<TemperatureService> temperatureService = new AnnotationLocalServiceBinder().read(TemperatureService.class);
        temperatureService.setManager(new DefaultServiceManager(temperatureService, TemperatureService.class));

        return new LocalDevice(identity, type, details, temperatureService);
    }

    public void run()
    {
        try
        {
            final UpnpService upnpService = new UpnpServiceImpl();

            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    upnpService.shutdown();
                }
            });

            upnpService.getRegistry().addDevice(createWeatherDevice());
        }
        catch (Exception ex)
        {
            System.err.println("Exception: " + ex);
            ex.printStackTrace();
        }
    }

}
