package webapi;

import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.model.message.header.STAllHeader;
import org.fourthline.cling.model.meta.DeviceIdentity;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.model.types.UDN;
import org.fourthline.cling.registry.Registry;
import org.fourthline.cling.registry.RegistryListener;

import org.fourthline.cling.controlpoint.*;
import org.fourthline.cling.model.action.*;
import org.fourthline.cling.model.message.*;
import org.fourthline.cling.model.message.header.*;
import org.fourthline.cling.model.meta.*;
import org.fourthline.cling.model.types.*;
import org.fourthline.cling.registry.*;

public class Client implements Runnable {

    public static void main(String[] args) throws Exception {

        // UPnP discovery is asynchronous, we need a callback
        RegistryListener listener = new RegistryListener() {

            public void remoteDeviceDiscoveryStarted(Registry registry,
                                                     RemoteDevice device) {
                System.out.println(
                        "Discovery started: " + device.getDisplayString()
                );
            }

            public void remoteDeviceDiscoveryFailed(Registry registry,
                                                    RemoteDevice device,
                                                    Exception ex) {
                System.out.println(
                        "Discovery failed: " + device.getDisplayString() + " => " + ex
                );
            }

            public void remoteDeviceAdded(Registry registry, RemoteDevice device) {
                System.out.println(
                        "Remote device available: " + device.getDisplayString()
                );
            }

            public void remoteDeviceUpdated(Registry registry, RemoteDevice device) {
                System.out.println(
                        "Remote device updated: " + device.getDisplayString()
                );
            }

            public void remoteDeviceRemoved(Registry registry, RemoteDevice device) {
                System.out.println(
                        "Remote device removed: " + device.getDisplayString()
                );
            }

            public void localDeviceAdded(Registry registry, LocalDevice device) {
                System.out.println(
                        "Local device added: " + device.getDisplayString()
                );
            }

            public void localDeviceRemoved(Registry registry, LocalDevice device) {
                System.out.println(
                        "Local device removed: " + device.getDisplayString()
                );
            }

            public void beforeShutdown(Registry registry) {
                System.out.println(
                        "Before shutdown, the registry has devices: "
                                + registry.getDevices().size()
                );
            }

            public void afterShutdown() {
                System.out.println("Shutdown of registry complete!");

            }
        };

        // This will create necessary network resources for UPnP right away
        System.out.println("\t ### Starting Cling Client...");

        // UpnpService upnpService = new UpnpServiceImpl(listener);
        // Send a search message to all devices and services, they should respond soon
        //upnpService.getControlPoint().search(new STAllHeader());


        // Create control point
        // Start a user thread that runs the UPnP stack
        Thread clientThread = new Thread(new Client());
        clientThread.setDaemon(false);
        clientThread.start();
    }


    public void run() {
        System.out.println("\t ### Starting run function...");

        try {
            UpnpService upnpService = new UpnpServiceImpl();

            // Add a listener for device registration events
            upnpService.getRegistry().addListener(
                    createRegistryListener(upnpService)
            );

            // Broadcast a search message for all devices
            upnpService.getControlPoint().search(
                    new STAllHeader()
            );

            // Let's wait 10 seconds for them to respond
            //System.out.println("Waiting 10 seconds before shutting down...");
            //Thread.sleep(10000);
            // Release all resources and advertise BYEBYE to other UPnP devices
            //System.out.println("Stopping Cling...");
            //upnpService.shutdown();
        } catch (Exception ex) {
            System.err.println("Exception occured: " + ex);
            System.exit(1);
        }

    }

    RegistryListener createRegistryListener(final UpnpService upnpService) {
        return new DefaultRegistryListener() {

            ServiceId serviceId = new UDAServiceId("Service");

            @Override
            public void remoteDeviceAdded(Registry registry, RemoteDevice device) {
                Service ourService;
                if ((ourService = device.findService(serviceId)) != null) {
                    System.out.println("[Client remoteDeviceAdded] Service discovered: " + ourService);
                    executeAction(upnpService, ourService);
                }
            }

            @Override
            public void remoteDeviceRemoved(Registry registry, RemoteDevice device) {
                Service ourService;
                if ((ourService = device.findService(serviceId)) != null) {
                    System.out.println("Service disappeared: " + ourService);
                }
            }

        };
    }

    void executeAction(UpnpService upnpService, Service ourService) {

        System.out.println("\t> executeAction");

        ActionInvocation setTargetInvocation = new GetWeightActionInvocation(ourService);

        // Executes asynchronous in the background
        upnpService.getControlPoint().execute(
                new ActionCallback(setTargetInvocation) {

                    @Override
                    public void success(ActionInvocation invocation) {
                        System.out.println("Successfully called action! (Client executeAction), result:"
                                + "\n\t===> " + invocation.getOutput("jsonOutput"));
                    }

                    @Override
                    public void failure(ActionInvocation invocation,
                                        UpnpResponse operation,
                                        String defaultMsg) {
                        System.err.println("FAIL! Client executeAction");
                        System.err.println(defaultMsg);
                    }
                }
        );
    }

    class GetWeightActionInvocation extends ActionInvocation {

        GetWeightActionInvocation(Service service) {
            super(service.getAction("GetWeight"));
            // Parameters values
            String parameters = "{\"apiKey\":\"03909c3fd264295cbbce7121c877dd0c2feb302f0c7e79f7240b0c9d8bfe\","
                    + "\"apiSecret\":\"7217b7ffc0b0d9599d3ec34d16b2be575b46789dbf0e8c7ce050611e248ff\","
                    + "\"accessToken\":\"01686f061b6154ed55912f6d207bb0217ee224d959f0332ed055507d54d64c6\","
                    +"\"secretToken\":\"0df0e4f38b1be7582b545e70d129e8b371616e03e7424ffbf95d10f77db4e\"}";
            // String objectName = "SmartBodyAnalyzer";
            String objectName = "WiiBoard";
            try {
                System.out.println("\t\tSetInputs");
                // Set parameters values
                setInput("parameters", parameters);
                setInput("objectName", objectName);
            } catch (InvalidValueException ex) {
                System.err.println(ex.getMessage());
                System.exit(1);
            }
        }
    }

}