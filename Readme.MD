Projet VDM
=========================

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
Le build se fera via Maven 3.  
Les tests se feront avec Junit, Spring Tests, AssertJ et Cucumber
Le reste est détaillé dans l'implémentation technique des différents besoins techniques ci dessous.

Les variables par défaut pour le fonctionnement du programme pourront étre surchargées.

## Implémentation de la récupération des derniéres VDM

Comme on ne peut pas utiliser l'API Rest, les solutions restantes sont :
- Parcours des pages web et extraction du contenu page par page
- Utilisation du flux RSS

La solution Flux rss semble la plus adaptée, car le format a peu de chances de changer, alors que la structure html de la page a de grandes chances d'évoluer. De plus, cela est plus simple et plus léger de parser un flux rss qu'une page html.

On utilisera une librairie de parsing xml, qui permettra de mapper le contenu sur des objets.

## Stockage des données

Afin de stocker les données, plusieurs solutions légéres (sans avoir é installer une application tierce) sont :
- Utilisation d'une base de donnnées embarquées (H2 ?)
- Sauvegarde dans un fichier json

La solution BDD est préférable, car elle permet de faire des requêtes SQL sur des champs (id, autheur, date).
La solution via json sera complexe à implémenter, et peu performante.
On utilisera une base H2. On aurait également pu utiliser HSQL ou Derby.

## Exposition d'une API Rest
On utilisera Spring MVC qui est logique d'utiliser dans une stack Spring gréce é sa bonne intégration avec Spring Boot. 
On mettra en place un nombre de résultats max retournés (afin d'éviter les problémes de performances si é terme le projet stockait un plus grand nombre de données)

## Récupération des données depuis le repository






