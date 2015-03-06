# Framework

Pour compiler et lancer le framework :

	$ mvn install spring-boot:run

Un éditeur permettant de rentrer les informations au format JSON (voir `/doc/2_Entree-du-systeme.md`) est alors disponible sur `http://localhost:8080/generator`.

La page est pré-remplie avec un JSON d'exemple. Veillez à modifier les paths de `javaModules` et `localJars` pour que cela corresponde avec avec votre arborescence.

En cliquant sur le bouton `Générer`, une archive ZIP va être proposée au téléchargement. Une fois dézipée, elle contient un projet Maven.

Celui-ci se compile et se lance de cette manière :

	$ mvn install
	$ java -jar target/Device-1.0-SNAPSHOT.jar

Il expose alors des services accessibles via UPnP (nom du dispositif : `Device`, nom du service : `Service`).