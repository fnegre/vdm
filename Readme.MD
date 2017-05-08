Projet VDM
=========================
# Lancement du projet 
Pour lancer le projet, il est nécessaire d'avoir Maven 3 (testé avec 3.3.9) et java 8.  
Cloner le repository  
Lancer la commande : `mvn spring-boot:run'  
L'api Rest est disponible sur : http://localhost:8081/api/posts

# Reste à faire
Par manque de temps, il reste beaucoup à faire :
- Ajout de tests (unitaires et intégration)
- Paramétrage de l'application (le nombre de vdm à télécharger est écrit en dur par exemple)
- La gestion des erreurs (peu de test sur les cas extrêmes, sur les valeurs des paramètres de l'url dans l'API, etc.)
- Nettoyage du code (refactoring du code dans certains cas serait le bienvenu (comme le filtrage de l'API rest), les dernières corrections ont été faites sans iDE : pb d'indentation à certains endroits)
- Mise à jour de la documentation technique suite au développement (des points de détails ont pu changer entre la conception et l'implémentation)


# Fonctionnalites

Le projet possède deux fonctionnalités :
- La récupération des dernières VDM depuis le site http://www.viedemerde.fr
- La mise en place d'une API Rest permettant de consulter les VDM récupérées

## Fonctionnalité > Récupération des dernières VDM

### Détails 
Récupérer les 200 dernières VDM, puis stocker les informations suivantes : contenu, date, heure et auteur

Contraintes : 
- Ne pas utiliser l'API Rest proposé par le site

# Implémentation technique
## Architecture logicielle et orientation

On utilisera Java 8 avec une stack Spring Boot (derniére release). 
Cela permet un développement rapide grâce à son principe de configuration par convention.
On initialisera le projet avec Spring Initializr : https://start.spring.io/
Le build se fera via Maven 3.  
On utilisera le serveur Tomcat embarqué.
Les tests se feront avec Junit, Spring Tests, AssertJ et Cucumber
Le reste est détaillé dans l'implémentation technique des différents besoins techniques ci dessous.
On utilisera Logback comme librairie de logging

Les variables par défaut pour le fonctionnement du programme pourront étre surchargées.

## Stockage et interrogation des données

Afin de stocker les données, plusieurs solutions légéres (sans avoir é installer une application tierce) sont :
- Utilisation d'une base de donnnées embarquées (H2 ?)
- Sauvegarde dans un fichier json

La solution BDD est préférable, car elle permet de faire des requêtes SQL sur des champs (id, autheur, date).
La solution via json sera complexe à implémenter, et peu performante.
On utilisera une base H2. On aurait également pu utiliser HSQL ou Derby.

Les requêtes vers la BDD se feront via Spring Data, qui est un outil répondant aux besoins (insertions, requêtes paramétrées)

## Implémentation des fonctionnalités 

### Implémentation de la récupération des derniéres VDM

Comme on ne peut pas utiliser l'API Rest, les solutions restantes sont :
- Parcours des pages web et extraction du contenu page par page
- Utilisation du flux RSS

La solution Flux rss semble la plus adaptée, car le format a peu de chances de changer, alors que la structure html de la page a de grandes chances d'évoluer. De plus, cela est plus simple et plus léger de parser un flux rss qu'une page html.

On utilisera une librairie de parsing xml, qui permettra de mapper le contenu sur des objets.
L'url du flux rss est http://www.viedemerde.fr/rss
Il affiche 20 articles à la fois, et il contient bien les informations demandées. 
Afin d'afficher les VDM suivantes, il faut utiliser le paramètre page.
Exemple : http://www.viedemerde.fr/rss?page=3

Structure d'une VDM dans le flux :
```
<entry xmlns="http://www.w3.org/2005/Atom">
        <title type="html"><![CDATA[Par vieille peau]]></title>
        <id>211304</id>
        <link rel="alternate" href="http://www.viedemerde.fr/article/aujourd-hui-ma-niece-m-a-demande-mon-age-je-lui-ai-donc-repondu-que-j-avais-28-ans-elle-s-est_211304.html"/>
        <published>2017-05-04T20:00:02+02:00</published>
        <updated>2017-04-28T21:55:38+02:00</updated>
        <author>
            <name><![CDATA[vieille peau]]></name>
        </author>
        <content type="html"><![CDATA[<p>Aujourd'hui, ma nièce m'a demandé mon âge. Je lui ai donc répondu que j'avais 28 ans. Elle s'est mise à me serrer très fort et m'a dit le plus tristement du monde : "Je ne veux pas que tu ailles au ciel tata !" VDM</p>]]></content>
    </entry>
```

### Exposition d'une API Rest
On utilisera Spring MVC qui est logique d'utiliser dans une stack Spring gréce é sa bonne intégration avec Spring Boot. 
On mettra en place un nombre de résultats max retournés (afin d'éviter les problémes de performances si é terme le projet stockait un plus grand nombre de données)

### Récupération des données depuis le repository






