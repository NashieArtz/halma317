# H A L M A 

 > Vous avez le droit de modifier ce fichier. Voici quelques informations techniques pour faciliter le démarrage TP1.

Ce project et un projet Maven. N'utilisez pas le triangle vert de votre IDE aveuglement, parce que par défaut cela ignore la configuration fournie. Utilisez les commandes en dessus pour interagir avec votre project.
Vous avez l'option de [configurer le comportement du triangle vert](https://www.jetbrains.com/help/idea/run-debug-configuration-maven.html), pour que ça déclenche les commandes en dessous (instructions plus bas).

> Rappel: Ne modifiez pas le fichier `pom.xml` (configuration maven fourni). **Si vous le changez, votre soumission sera réjetté.**

## Quoi implémenter

Vous devez implémenter plusieurs classes et méthodes pour ce premier TP. Votre implémentation **doit** être conforme [aux interfaces fournies](https://inf2050.pages.info.uqam.ca/halma/halmainterfaces). Cela inclut toute information textuellement spécifiée dans la documentation, par exemple les déscriptions des interfaces.

Le code *source* des interfaces, et classes automatiquement accessibles pour votre implementation est également disponible sur GitLab: [https://gitlab.info.uqam.ca/inf2050/halma/halmainterfaces](https://gitlab.info.uqam.ca/inf2050/halma/halmainterfaces)

Spécifiquement, les classes suivantes sont demandées :

### Code de production

* Une classe dans : `src/main/java/ca/uqam/info/solanum/student/halma/controller/`. Le dossier **doit** contenir une classe **implémentant** [l'interface fourni `ModelFactory`](https://inf2050.pages.info.uqam.ca/halma/halmainterfaces/ca/uqam/info/solanum/inf2050/f24halma/controller/ModelFactory.html), en utilisant le mot clé `implements`. Vous dévez implémenter les méthodes suivantes :
  * [`createModel`](https://inf2050.pages.info.uqam.ca/halma/halmainterfaces/ca/uqam/info/solanum/inf2050/f24halma/controller/ModelFactory.html#createModel(int,java.lang.String%5B%5D))
* Une classe dans : `src/main/java/ca/uqam/info/solanum/student/halma/model/`. Le dossier **doit** contenir une classe **implémentant** [l'interface fourni `Model`](https://inf2050.pages.info.uqam.ca/halma/halmainterfaces/ca/uqam/info/solanum/inf2050/f24halma/model/ModelReadOnly.html), en utilisant le mot clé `implements`. Vous devez implémenter toutes les 5 méthodes spécifiées dans [sa classe superieure ModelReadOnly](https://inf2050.pages.info.uqam.ca/halma/halmainterfaces/ca/uqam/info/solanum/inf2050/f24halma/model/ModelReadOnly.html), mais aucune [des trois fournis par l'extension Model](https://inf2050.pages.info.uqam.ca/halma/halmainterfaces/ca/uqam/info/solanum/inf2050/f24halma/model/Model.html).
* Une classe dans : `src/main/java/ca/uqam/info/solanum/student/halma/model/`. Le dossier **doit** contenir une classe **implémentant** [l'interface fourni `Board`](https://inf2050.pages.info.uqam.ca/halma/halmainterfaces/ca/uqam/info/solanum/inf2050/f24halma/model/Board.html), en utilisant le mot clé `implements`. Vous devez implémenter seulement implémenter les méthodes suivantes :
  * [`getAllFields`](https://inf2050.pages.info.uqam.ca/halma/halmainterfaces/ca/uqam/info/solanum/inf2050/f24halma/model/Board.html#getAllFields())
  * [`getAllHomeFields`](https://inf2050.pages.info.uqam.ca/halma/halmainterfaces/ca/uqam/info/solanum/inf2050/f24halma/model/Board.html#getAllHomeFields())
  * [`getHomeFieldsForPlayer`](https://inf2050.pages.info.uqam.ca/halma/halmainterfaces/ca/uqam/info/solanum/inf2050/f24halma/model/Board.html#getHomeFieldsForPlayer(int))
  * [`getTargetFieldsForPlayer`](https://inf2050.pages.info.uqam.ca/halma/halmainterfaces/ca/uqam/info/solanum/inf2050/f24halma/model/Board.html#getTargetFieldsForPlayer(int))
  
  Pour le TP1, il n'est pas nécessaire d'implementer les méthodes suivantes :
  * [`getNeighbours`](https://inf2050.pages.info.uqam.ca/halma/halmainterfaces/ca/uqam/info/solanum/inf2050/f24halma/model/Board.html#getNeighbours(ca.uqam.info.solanum.inf2050.f24halma.model.Field))
  * [`getExtendedNeighbour`](https://inf2050.pages.info.uqam.ca/halma/halmainterfaces/ca/uqam/info/solanum/inf2050/f24halma/model/Board.html#getExtendedNeighbour(ca.uqam.info.solanum.inf2050.f24halma.model.Field,ca.uqam.info.solanum.inf2050.f24halma.model.Field))
  Vous pouvez simplement lancer une exception dans ces deux méthodes (ainsi que pour les trois méthodes non-utilisés dans votre implémentation Modèle):  
  `throw new RuntimeException("Not yet implemented");`

 > Pourquoi la `Factory` ? Sélon la logique MVC, on ne veut aucune logique dans le package Model. Pourtant, la creation du Modèle nécessite un peu de logique. En utilisant [le patron de conception `Factory`](https://refactoring.guru/fr/design-patterns/factory-method), on peut garder toute logique dans le controller, cépendant les données du Modèle resteront dans leur package approprié.
  
### Code de tests

* Pour pouvoir accéder les tests existants, il suffira de compléter les deux classes de tests dans :  
`src/test/java/ca/uqam/info/solanum/student/halma/*/`
* Chaque classe nécessite qu'une seule méthode.
* Une fois implémenté, vous pouvez tester votre soumission en utilisant la commande maven correspondante. Alternativement vous pouvez lancer un clique droit sur le package de test, suivi par "lancer tous les tests".

 > Cette partie **n'est pas optionnel**. Si vous ne fournissez pas les deux classes de test, qui `extends` les classes de test fournies, **votre solution sera rejeté**.

## References

Vous trouvez plusieurs "étoiles" [sur le site TP1](https://inf2050.uqam.ca/fr/1tp.html#dispositions-de-reference).

## Commandes terminal

 * Compiler et executer tests fournis, sans lancer jeu:
`mvn clean package`

 * Compiler et lancer jeu, sans tester:
`mvn clean compile exec:java -Dexec.args="1 Max Quentin Jean"`

 * Compiler, executer les tests, et lancer le jeu:
`mvn clean package exec:java -Dexec.args="1 Max Quentin Jean"`

> Pour lancer le jeu, remplacez `1` par un autre chiffre positif, pour augmenter la taille du terrain du jeu. Le chiffre indique la taille des "bases" (triangles des joueurs).


## Profils d'exécution IntellliJ

* Les profils d'exécution définissent *comment* votre code est compilé et démarré.
* Intuitivement, on pourrait penser que c'est trivial, car il y a des triangles verts invitants ("<text style="color:green">&#9654;</text>") partout dans l'éditeur.
  <img src="https://resources.jetbrains.com/help/img/idea/2024.2/ij-run-current-file.png" width="80%" style="display: block; margin-left: auto; margin-right: auto;"/>

* Cependant, par défaut, l'IDE ne sait rien d'un système de build que nous pourrions utiliser.

 > Avertissement: Les configurations d'exécution par défaut sont pour des projets Java minimaux, c'est-à-dire des projets sans paramètres et sans dépendances. Elles ne déclenchent **pas** Maven ; en utilisant les triangles verts, vous ne bénéficierez pas des avantages d'un système de build.

Avant de pouvoir développer sur un projet Maven, nous devons indiquer à l'IDE quelles sont nos intentions :

* Quelle configuration du système de build utiliser
* Ce qui doit se passer lorsque nous "exécutons" notre projet

#### Chargement de la configuration du système de build

* IntelliJ est souvent bon pour deviner à partir des fichiers trouvés dans un projet.
* Cependant, il est plus fiable de dire explicitement à IntelliJ quel type de projet nous avons.
    * Comme vous vous en souvenez d'une précédente leçon, des bibliothèques supplémentaires peuvent être configurées dans le `pom.xml` de Maven (par exemple, JUnit).
    * Maven modifie le classpath pour nous, nous pouvons utiliser ces dépendances.
    * Si IntelliJ ne sait pas que nous utilisons Maven, le classpath reste inchangé et nous verrons beaucoup d'erreurs chaque fois que nous essaierons de travailler avec un artefact externe du JDK.
* Pour dire explicitement à IntelliJ que nous utilisons le système de build Maven :
    1. Faites un clic droit sur le fichier `pom.xml` à gauche
    2. Sélectionnez `Ajouter en tant que projet Maven -> Recharger`

#### Configuration d'une configuration de lancement Maven

* Nous pouvons [indiquer à IntelliJ d'utiliser Maven](https://www.jetbrains.com/help/idea/run-debug-configuration-maven.html) lorsque le triangle vert en haut ("<text style="color:green">&#9654;</text>") est cliqué.
    1. Cliquez sur le menu déroulant (à côté de l'icône "<text style="color:green">&#9654;</text>")
    2. Sélectionnez "Modifier les configurations..."
    3. Cliquez sur l'icône "+" puis sélectionnez l'option "Maven" (⌥) :
    4. Donnez un nom à votre configuration et spécifiez la commande Maven (⌘) à exécuter :
       <img src="https://resources.jetbrains.com/help/img/idea/2024.2/maven_run_configuration.png" width="100%" style="display: block; margin-left: auto; margin-right: auto;"/>

    * Pour le champ **Exécuter**, omettez l'instruction initiale "mvn".
    * Exemple : si vous souhaitez appeler `mvn clean package`, chaque fois que vous cliquez sur "<text style="color:green">&#9654;</text>", tapez simplement `clean package`.

* À partir de maintenant, vous pouvez exécuter votre programme avec Maven, en utilisant le lanceur *supérieur*.
* Vous pouvez également répéter le dernier appel du lanceur avec : `Control (⌃) + R`

 > Astuce: Définissez plusieurs configurations d'exécution pour diverses commandes Maven, par exemple, une pour construire un jar, une pour exécuter uniquement des tests, une pour exécuter votre code, etc. Nous apprendrons bientôt comment utiliser efficacement la ligne de commande Maven (⌘) pour exécuter des phases spécifiques du système de build.








