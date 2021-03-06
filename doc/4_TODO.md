# TODO

## Créer des modules

L'intérêt pour le développeur d'utiliser notre framework réside également dans le fait de ne pas avoir à redévelopper chaque couche du protocole de communication. Nous avons donc fait le choix de développer des modules génériques, qui seront embarqués dans l'archive générée. Ceux-ci seront alors appelés par le serveur UPnP avec les bons paramètres, résumant dans de nombreux cas cet appel à une simple ligne.

Actuellement, deux modules permettant une communication via services web sont développés. Le premier (`WSHandler`) permet de se connecter à un service web standard, alors que le second (`OAuthHandler`) offre l'utilisation de la surcouche de sécurité OAuth.

Il est important de développer davantage de modules génériques afin que l'utilisateur n'ait pas à les redévelopper lui-même. On peut alors imaginer un module de communication Bluetooth, un autre de communication NFC...

Pour ajouter un module, il suffit de l'ajouter dans le package `.modules`. Puis, dans la classe `.engine.MethodContentParser`, gérer l'utilisation de ce module en écrivant le bout de code qui l'appelera, en spécifiant les bons paramètres.

## Gestion des imports

Dans cette première version, la classe Java générée correspondant à un service UPnP importe tous les modules existants. De même, tous les modules existants sont importés dans l'archive générée.

Pour des raisons d'optimisation de la taille de l'archive générée, il est important de n'inclure et de n'importer que ce qui va être effectivement utilisé. Pour le moment cela n'est pas vraiment gênant, mais lorsque davantage de modules seront créés ils seront pour la plupart inclus dans l'archive inutilement.

## Gérer la notion d'évènement

La pile techologique choisie permet au proxy généré de gérer les évènements.

Cependant, ceci n'est pas encore implémenté.

Exemple : le mesure mon pouls avec un bracelet connecté, celui-ci en informe le proxy, qui lève à son tour en évènement UPnP.

## Choix automatique de l'objet

Actuellement, l'objet à partir duquel récupérer l'information est fourni en paramètre de la méthode.

Idéalement, le proxy choisirait lui-même quel objet utiliser. Ceci pose cependant plusieurs problèmes : comment choisir l'objet à utilier ? Comment peut-on être sûr qu'il est accessible ? Que la valeur qu'il va retourner est cohérente avec celle que les autres objets auraient retourné ?

## Lien avec le client Cling

Actuellement le serveur web, situé dans la partie `demonstrator/server` du projet, expose des services web permettant la récupération des données des différents aux objets au format JSON. D'autre part, le client Cling a également été développé (`Client.java` également situé dans le dossier du `demonstrator/server`), communiquant directement avec le proxy et récupérant les données dynamiquement. Cependant, le lien entre ces deux parties reste à faire.