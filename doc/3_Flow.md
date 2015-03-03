# Flow

L'entrée est un fichier de description au format JSON; la sortie est une archive ZIP contenant un projet compilable avec Maven.

## 1. Parser le JSON

La première étape est de récupérer un objet Java de type `Input` à partir du JSON passé en entrée.

Pour ce faire, on utilise le singleton `InputParser`.

    Input input = InputParser.getInstance().parse(json);

## 2. Créer un UPnP device

L'idée est d'extraire de l'objet `Input` deux informations : un `UpnpDevice` et un objet `Requirements` qui contiendra les éléments requis (JAR local, dépendance Maven...).

Le singleton `InputToUpnpDevice` a été mis en place pour cela.

    Pair<UpnpDevice, Requirements> device = InputToUpnpDevice.getInstance().getDevice(input);

## 3. Créer le projet Maven

Le singleton `MavenProjectGenerator` se charge de transformer le `UpnpDevice` et ses `Requirements` en un projet Maven.

    MavenProjectGenerator.getInstance().generateMavenProject(upnpDevice, requirements);

Une archive `project.zip` est alors créée.