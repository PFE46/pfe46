# Entrée du système

## Introduction

L'entrée dans le système se fait en utilisant une chaîne de caractères au format JSON respectant les conventions suivantes :

- une liste d'objets, correspondants aux objets connectés ;
- une liste de méthodes;
- une liste de dépendances.

## Les objets

Les objets connectés sont représentés dans le package `.engine.inputtype.objects`.

Un objet hérite de la classe `ConnectedObject` et doit correspondre à l'une des classes qui en hérite au sein du package (e.g. `BluetoothObject`).

## Les méthodes

Les méthodes sont représentées dans le package `.engine.inputtype.methods`.

Une méthode est de la classe `Method` et a une liste de `MethodBinding`. Une "méthode binding" hérite de la classe `MethodBinding` et doit correspondre à l'une des classes qui en hérite au sein du package (e.g. `WsRestMethodBinding`).

L'attribut `object` du binding doit correspondre au nom de l'un des objets définis dans la liste d'objets connectés.

## Exemple

#### Contexte

Dans l'exemple suivant nous disposons de deux objets connectés :

- une Wii Balance Board, qui fonctionne en Bluetooth mais en passant par la librairie `WiiRemoteJ`;
- un pèse-personnes Withings Smart Body Analyzer, qui expose ses services via un service web REST sécurisé avec OAuth.

On souhaite générer un proxy qui permette de récupérer le poids sur ces deux objets.

#### Définition des objets

La première étape est de définir la liste des objets connectés auxquels sera relié le proxy.

###### Wii Balance Board

La communication avec la Wii Balance Board s'effectue en Bluetooth. Cependant, la communication avec celle-ci étant complexe, nous préférons passer par une librairie. On définit donc le protocole à `LIBRARY` en spécifiant qu'il s'agit d'un JAR (`libraryType`).

###### Withings Smart Body Analyzer

Avec cet objet, l'accès se fait dans la très grande majorité des cas via un service web REST (protocole `WS_REST`), qui est sécurisé avec OAuth.

Pour gérer OAuth, on indique que l'accès aux données est sécurisé avec ce protocole (attribut `useOAuth`) et on indique le fournisseur de l'accès sécurisé, ici `Withings`.

#### Définition des méthodes

Après avoir défini les objets, il faut définir les méthodes qui seront exposées. Dans notre cas, on en expose qu'une seule, à savoir une méthode qui permette de récupérer le poids. On lui donne donc le nom `getWeight`.

Ensuite, il faut lier cette méthode à celles fournies par les objets connectés. Cela se fait dans la liste de bindings.

###### Pour la Wii Balance Board

La première étape est d'indiquer l'objet connecté spécifié au dessus auquel sera lié cette méthode, ici `WiiBoard`.

Comme la communication s'établit avec la librairie, on spécifie le code qui sera intégré dans le service UPnP généré. Pour plus d'informations, voire le paragraphe (`Définition des dépendances`).

###### Pour le Withings Smart Body Analyzer

De la même manière que pour la Wii Balance Board, on doit indiquer l'objet : `SmartBodyAnalyzer`.

Comme l'accès à la donnée se fait via un service web REST, on spécifie le endpoint et le verbe HTTP.

#### Définition des dépendances

Pour utiliser la librairie `WiiRemoteJ`, deux dépendances Maven sont requises : `bluecove` et `bluecove-gpl`.

On spéficie également le chemin du JAR de `WiiRemoteJ` dans `localJars`.

Comme le code utilisant la librairie reste "compliqué", nous l'impliquons sous forme de module. De même que pour le JAR, nous spécifions le chemin d'accès. Le code d'exemple correspondant est disponible dans le dossier `src/main/resources/WiiBalance/`.

Enfin, le code qui sera inséré dans la méthode du service UPnP (`methodCode`) est donc celui qui appelle ce module.

#### Exemple

###### Fichier en entrée

Le fichier JSON correspondant est donc le suivant :

    {
      "objects":[
        {
          "name"       : "WiiBoard",
          "protocol"   : "LIBRARY",
          "libraryType": "JAR",
          "id"         : "WiiRemoteJ"
        },
        {
          "name"    : "SmartBodyAnalyzer",
          "protocol": "WS_REST",
          "useOAuth": true,
          "provider": "Withings"
        }
      ],
      "methods":[
        {
          "name":"getWeight",
          "bindings":[
            {
              "object"    : "WiiBoard",
              "methodCode": "
                BBImpl bbimpl = new BBImpl();
                Double res = bbimpl.getWeight();
                // Round Double result to two decimal places
                res = (double) Math.round(res * 100);
                res = res/100;
                return res;
              "
            },
            {
              "object"  : "SmartBodyAnalyzer",
              "endpoint": "https://wbsapi.withings.net/measure?action=getmeas&meastype=1",
              "verb"    : "GET"
            }
          ]
        }
      ],
      "mavenDependencies":[
        {
          "groupId"   : "net.sf.bluecove",
          "artifactId": "bluecove",
          "version"   : "2.1.0"
        },
        {
          "groupId"   : "net.sf.bluecove",
          "artifactId": "bluecove-gpl",
          "version"   : "2.1.0"
        }
      ],
      "localJars":[
        "/Users/victorsalle/Cours/PFE/pfe46/framework/src/main/resources/WiiBalance/WiiRemoteJ.jar"
      ],
      "javaModules":[
        "/Users/victorsalle/Cours/PFE/pfe46/framework/src/main/resources/WiiBalance/BBImpl.java"
      ]
    }

###### Projet en sortie
    
Ce qui va générer le projet suivant :

    .
    ├── localrepo
    │   └── com
    │       └── WiiRemoteJ
    │           └── 1.0
    │               └── WiiRemoteJ-1.0.jar
    ├── pom.xml
    └── src
        └── main
            └── java
                ├── DeviceServer.java
                ├── Service.java
                └── modules
                    ├── BBImpl.java
                    ├── JsonProcess.java
                    ├── Modules.java
                    ├── NoSuchProviderException.java
                    ├── OAuthHandler.java
                    ├── WSHandler.java
                    └── WithingsApi.java

Les fichiers inclus dans le répertoire `module` sont ceux qui sont dans le package `.modules` du framework, ainsi que ceux fournis en attribut `javaModules` du JSON d'entrée.

Les librairies JAR sont placées dans un repository local Maven. Le numéro de vesion est pour le moment codé en dur, mais cela n'a pas d'autre impacte qu'une mauvaise lisibilité.

Le `pom.xml` contient bien les dépendances `bluecove` et `bluecove-gpl` fournies.

Enfin, le `Service.java` expose la méthode suivante :

    @UpnpAction(out = @UpnpOutputArgument(name = "jsonOutput"))
    public String getWeight(@UpnpInputArgument(name = "parameters") String parameters,        
    						@UpnpInputArgument(name = "objectName") String objectName)
    	throws NoSuchProviderException
    {
    	if (objectName.equals("WiiBoard"))
    	{
    		BBImpl bbimpl = new BBImpl();
    		Double res = bbimpl.getWeight();
    		res = (double) Math.round(res * 100);
    		res = res/100;
    		return String.valueOf(res);
    	}
    	else if (objectName.equals("SmartBodyAnalyzer"))
    	{
    		Map<String, String> params = JsonProcess.jsonToMap(parameters);
    		String res = OAuthHandler.getInstance()
    								 .callServiceGet("Withings", "https://wbsapi.withings.net/measure?action=getmeas&meastype=1",
    								 				 params.get("apiKey"), params.get("apiSecret"), params.get("accessToken"),
    								 				 params.get("secretToken"), params);
    		return res;
    	}
    	
    	return "{\"error\":\"true\"}";
    }
    
Le code correspondant à l'appel au service web REST sécurisé par OAuth a été généré automatiquement puisque existant comme module générique. Pour la balance WiiBoard en revanche, s'agissant d'un module fourni par l'utilisateur, le code est celui renseigné dans le fichier JSON.

Pour tester le service généré, il est possible de l'appeler avec des paramètres :

* Wii Balance Board
	* `parameters`: `{}`
	* `objectName`: `WiiBoard`

* Smart Body Analyzer
	* `parameters`: `{"apiKey": "x", "apiSecret": "x", "accessToken": "x", "secretToken": "x"}`
	* `objectName`: `SmartBodyAnalyzer`