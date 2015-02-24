# Entrée du système

## Introduction

L'entrée dans le système se fait en utilisant une chaîne de caractères au format JSON respectant les conventions suivantes :

- une liste d'objets, correspondants aux objets connectés ;
- une liste de méthodes.

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

- une Wii Balance Board, qui fonctionne en Bluetooth ;
- un pèse-personnes Withings Smart Body Analyzer, qui expose ses services via un service web REST.

On souhaite générer un proxy qui permette de récupérer le poids sur ces deux objets.

#### Définition des objets

La première étape est de définir la liste des objets connectés auxquels sera relié le proxy.

###### Wii Balance Board

La communication avec la Wii Balance Board s'effectue en Bluetooth. On définit donc le protocole à `BLUETOOTH` en spécifiant l'identifiant Bluetooth de l'objet (`deviceId`).

###### Withings Smart Body Analyzer

Avec cet objet, l'accès se fait dans la très grande majorité des cas via un service web REST (protocole `WS_REST`), qui est sécurisé avec OAuth.

Pour gérer OAuth, on indique que l'accès aux données est sécurisé avec ce protocole (attribut `useOAuth`) et on indique le fournisseur de l'accès sécurisé, ici `Withings`.

#### Définition des méthodes

Après avoir défini les objets, il faut définir les méthodes qui seront exposées. Dans notre cas, on en expose qu'une seule, à savoir une méthode qui permette de récupérer le poids. On lui donne donc le nom `getWeight`.

Ensuite, il faut lier cette méthode à celles fournies par les objets connectés. Cela se fait dans la liste de bindings.

###### Pour la Wii Balance Board

La première étape est d'indiquer l'objet connecté spécifié au dessus auquel sera lié cette méthode, ici `WiiBoard`.

Comme la communication s'établit en Bluetooth, on utilise ici un objet de classe `BluetoothMethodBinding`. Enfin, l'adresse Bluetooth pour récupérer le poids est `getWiiBoardWeightAddress`.

###### Pour le Withings Smart Body Analyzer

De la même manière que pour la Wii Balance Board, on doit indiquer l'objet : `SmartBodyAnalyzer`.

Comme l'accès à la donnée se fait via un service web REST, on spécifie le endpoint et le verbe HTTP.

#### Résultat

Le fichier JSON correspondant est donc le suivant :

    {
      "objects": [
        {
          "name": "WiiBoard",
          "protocol": "BLUETOOTH",
          "deviceId": "wiiboardid"
        },
        {
          "name": "SmartBodyAnalyzer",
          "protocol": "WS_REST",
          "useOAuth": true,
          "provider": "Withings"
        }
      ],
      "methods": [
        {
          "name": "getWeight",
          "bindings": [
            {
              "object": "WiiBoard",
              "bluetoothMethod": "getWiiBoardWeightAddress"
            },
            {
              "object": "SmartBodyAnalyzer",
              "endpoint": "/api/measures",
              "verb": "GET"
            }
          ]
        }
      ]
    }
