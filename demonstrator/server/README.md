# Serveur web

## Launch server

Afin de lancer le serveur web :
- Se placer au niveau du fichier pom.xml ;
- Lancer les commandes suivantes :

	$ mvn clean install
	$ mvn spring-boot:run
	
## Utilisation

Par défaut, les services exposés le sont à l'adresse `http://localhost:8080/`.
Afin de vérifier si le serveur est correctement lancé, il suffit de se connecter à l'URL `http://localhost:8080/test`.