package fr.unice.polytech.si5.pfe46.generated;

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

    public LocalDevice createDevice() throws ValidationException
    {
        DeviceIdentity identity = new DeviceIdentity(UDN.uniqueSystemIdentifier("Weather"));
        DeviceType type = new UDADeviceType("Weather", 1);
        DeviceDetails details = new DeviceDetails("Weather Station",
                                                    new ManufacturerDetails("PFE46"));

        LocalService<TemperatureService> TemperatureService = new AnnotationLocalServiceBinder().read(TemperatureService.class);
        TemperatureService.setManager(new DefaultServiceManager(TemperatureService, TemperatureService.class));
        
        return new LocalDevice(identity, type, details, new LocalService[] {
            TemperatureService
        });
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

            upnpService.getRegistry().addDevice(createDevice());
        }
        catch (Exception ex)
        {
            System.err.println("Exception: " + ex);
            ex.printStackTrace();
        }
    }

}
