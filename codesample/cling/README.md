# Cling

Documentation complète : http://4thline.org/projects/cling/core/manual/cling-core-manual.html

La documentation peut sembler assez longue, mais elle est finalement assez simple. L'idée générale est présentée ici, le reste de la doc proposant des choses plus spécifiques sur lesquelles il faudra peut-être jeuter un oeil lors du développement.

## Installation

Ajouter les dépendances et repository Maven : http://4thline.org/projects/cling/core/

Ne pas oublier d'inclure la lib dans le JAR généré par Maven.

## Implémentation d'un service

Un device peut avoir plusieurs services.

#### Création de la classe de base

    @UpnpService( serviceId   = @UpnpServiceId("ServiceName"),
                  serviceType = @UpnpServiceType(value = "ServiceName", version = 1) )
    public class ServiceName {
    
    }

#### Ajout d'une variable d'état

Dans la classe :

    @UpnpStateVariable(defaultValue = "0", sendEvents = false)
    private boolean variableState = false;
    
Ou en tant qu'annotation :

    @UpnpStateVariables(
        {
            @UpnpStateVariable(name = "VariableState", datatype = "boolean", defaultValue = "0", sendEvents = false)
        }
    )
    public class TemperatureService {
    
    }

#### Ajout d'une méthode

###### WARNING

Il semblerait que pour chaque retour et chaque paramètre, il faille déclarer une variable d'état.

J'ai essayé sans, et j'ai eu une exception. Du coup en cherchant je suis tombé sur le forum de Cling : "Every action argument is related to a state variable. This is UPnP.". Ça a été posté par celui qui a écrit la lib Cling, donc c'est fiable.

http://mailinglists.945824.n3.nabble.com/upnpaction-with-no-mapped-state-variables-tp2833565p2833595.html

###### Pas de paramètre et pas de retour

    @UpnpAction
    public void method1()
    {
        // ...
    }

###### Avec paramètre mais sans retour

    @UpnpAction
    public void method2( @UpnpInputArgument(name = "ArgumentName") boolean argumentName )
    {
        // ...
    }

###### Avec paramètre et retour

    @UpnpAction(out = @UpnpOutputArgument(name = "OutputName"))
    public void method3( @UpnpInputArgument(name = "ArgumentName") boolean argumentName )
    {
        // ...
    }

## Création d'un device

#### Informations sur le device

###### Générer un identifiant unique (UDN) qui sera toujours le même quand exécuté sur la même machine.

    DeviceIdentity identity = new DeviceIdentity(UDN.uniqueSystemIdentifier("DeviceName"));

Il faut faire attention ici à la notion de "exécuté sur la même machine", puisque le code est supposé être portable. A voir si on ne peut pas utiliser autre chose du type :

    DeviceIdentity identity = new DeviceIdentity(new UDN("uuid"));

Mais aucune doc pour ce constructeur donc à creuser.

###### Type du device

    DeviceType type = new UDADeviceType("DeviceName", nbVersion);

###### Détails

    DeviceDetails = new DeviceDetails("Friendly Name", new ManufacturerDetails("Manufacturer name"));

#### Collecter les services

Pour chaque service :

    LocalService<ServiceClass> service = new AnnotationLocalServiceBinder().read(ServiceClass.class);
    
    service.setManager(new DefaultServiceManager(service, ServiceClass.clas));

#### Créer un device

Device avec un seul service :

    LocalDevice device = new LocalDevice(identity, type, details, service);

Device avec plusieurs services :

    LocalDevice device = new LocalDevice(identity, type, details, new LocalService[] {service1, service2});

## Lancer le serveur

    public class DeviceNameServer implements Runnable {

        public static void main(String[] args) throws Exception {
            Thread serverThread = new Thread(new DeviceNameServer());
            serverThread.setDaemon(false);
            serverThread.start();
        }

        public void run() {
            try {
                final UpnpService upnpService = new UpnpServiceImpl();

                Runtime.getRuntime().addShutdownHook(new Thread() {
                    @Override
                    public void run() {
                        upnpService.shutdown();
                    }
                });

				LocalDevice device = createDevice(); // (cf "Création d'un device")
                upnpService.getRegistry().addDevice(device);
            } catch (Exception ex) {
                System.err.println("Exception occured: " + ex);
                ex.printStackTrace(System.err);
                System.exit(1);
            }
        }

    }

## Codes d'exemple

Deux codes d'exemple : le premier, BinaryLight. C'est simplement celui qui est donné dans la doc de Cling.

Le second dispo dans le package `weather` : j'ai essayé de faire un truc tout simple qui récupère la météo d'une ville via une API. J'aurais aimé gérer des Double plutôt que des Int, mais je n'y suis pas arrivé en UPnP.

Du coup pour l'instant les types qui ont été testés et qui marchent : `string`, `boolean`, `int`.

Pour lancer le device météo :

    $ mvn install
    $ java -jar target/cling-1.0-SNAPSHOT.jar