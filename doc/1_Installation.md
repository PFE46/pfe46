# Installation

Le projet utilise Maven pour être compilé. Ce gestionnaire de dépendances (en version 3 ou supérieure) et un JDK (1.7 ou supérieur) doivent donc être installés sur la machine de développement.

Trois environnements différents ont été utilisés :
	
* Machine 1
	* Mac OS X 10.10.1
	* JDK Oracle 1.8.0_20
	* Maven 3.2.3

* Machine 2
	* Ubuntu 14.04.2 LTS 32bits
	* JDK Oracle 1.7.0_75
	* Maven 3.0.5

Les sorties d'un `mvn -v` sont les suivantes :

	machine1 » mvn -v
	Apache Maven 3.2.3 (33f8c3e1027c3ddde99d3cdebad2656a31e8fdf4; 2014-08-11T22:58:10+02:00)
	Maven home: /usr/local/Cellar/maven/3.2.3/libexec
	Java version: 1.8.0_20, vendor: Oracle Corporation
	Java home: /Library/Java/JavaVirtualMachines/jdk1.8.0_20.jdk/Contents/Home/jre
	Default locale: en_US, platform encoding: UTF-8
	OS name: "mac os x", version: "10.10.1", arch: "x86_64", family: "mac"
	
    ----------------------------------------------------------------------------------------
	
	machine2 » mvn -v
	Apache Maven 3.0.5
	Maven home: /usr/share/maven
	Java version: 1.7.0_75, vendor: Oracle Corporation
	Java home: /usr/lib/jvm/java-7-openjdk-i386/jre
	Default locale: fr_FR, platform encoding: UTF-8
	OS name: "linux", version: "3.13.0-46-generic", arch: "i386", family: "unix"

Pour vérifier l'installation :

    $ git clone https://VictorSalle@bitbucket.org/VictorSalle/pfe46.git
    $ cd pfe46/framework
    $ mvn install spring-boot:run
    
Si un ZIP est proposé au téléchargement en cliquant sur `Générer` sur `http://localhost:8080/generator` alors l'installation est fonctionnelle. (Veiller à bien modifier les valeurs de `PATH_TO_PROJECT_ROOT_FOLDER` dans `javaModules` et `localJars`.)