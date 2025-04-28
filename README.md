# Halma TP4 – Solution Étudiante

## Exigences système

- **Java** 21
  Téléchargement : https://jdk.java.net/21/
- **Maven** 3.6+
- **Git**
- (Optionnel) **IDE** : IntelliJ IDEA ou équivalent

---

## Installation du dépôt

```bash
git clone https://github.com/NashieArtz/halma317.git
cd halma317
```

## Compilation et Packaging
Pour compiler et créer le JAR exécutable :
```bash
mvn clean package
```
Le JAR généré se trouve dans target/Halma-jar-with-dependencies.jar (ou target/Halma.jar suivant votre configuration).

## Exécution
## Via Maven (plugin ``exec``)
Profil par défaut (MadMax) :
```bash
mvn exec:java -Dexec.args="1 Max Jean Hafedh"
```
Directement avec Java
```bash
java -jar target/Halma.jar 1 Max Jean Hafedh
```

## Interface en ligne de commande
```php-template
Usage: java -jar halma.jar <baseSize> <player1> <player2> … <playerN>
```

``<baseSize>`` :
- ``1`` → plateau en étoile taille 5×7 (3 joueurs)
- ``2`` → plateau en étoile taille 9×13 (6 joueurs)
- etc.
``<playerX>`` : nom du joueur ou ``AI`` pour une IA

## Profils Maven pour IA

| Profil | Classe IA                                                     | Activation                      |
|--------|---------------------------------------------------------------|---------------------------------|
| madmax | ``ca.uqam.info.solanum.students.halma.ai.MadMaxMoveSelector`` | actif par défaut (``-Pmadmax``) |
| keksli | ``ca.uqam.info.solanum.students.halma.ai.KeksliMoveSelector`` | ``-Pkeksli``                    |

### Exemple:
```bash
mvn exec:java -Pkeksli -Dexec.args="2 AI Quentin Hafedh Maram Roman"
```

## Tests
### Tests unitaires & d’intégration
- JUnit 4 & JUnit Jupiter
- Classes de tests abstraites fournies par la dépendance ``halmatests``

```bash
mvn test
```

### Couverture et Mutation Testing
- JaCoCo pour couverture de code
- PIT pour tests de mutation (profil ``mutation``)
```bash
mvn verify -Pmutation
```

## Structure du projet
| Répertoire              | Contenu                                                   |
|-------------------------|-----------------------------------------------------------|
| ``src/main/java	``      | Code source (``model``, ``controller``, ``view``, ``ai``) |
| ``src/test/java	``      | Tests unitaires et d’intégration                          |
| ``src/main/resources	`` | Ressources (si nécessaires)                               |
| ``pom.xml	``            | Configuration Maven                                       |
| ``README.md	``          | Documentation du projet                                   |

