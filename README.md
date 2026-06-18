# 📚 Kingo Biblio

Application de gestion de bibliothèque développée avec Spring Boot.

Ce projet permet de gérer les livres, les exemplaires physiques, les membres, les emprunts et les statistiques de la bibliothèque via une API REST.

---
## Cas d'utilisation

1. Ajouter un membre
2. Ajouter un livre
3. Ajouter un exemplaire
4. Enregistrer un emprunt
5. Enregistrer un retour
6. Consulter les statistiques
7. Identifier les retards

## 🚀 Fonctionnalités

### Gestion des livres

- Ajouter un livre
- Modifier un livre
- Supprimer un livre
- Consulter la liste des livres
- Rechercher un livre

### Gestion des exemplaires

- Ajouter un exemplaire
- Consulter les exemplaires disponibles
- Vérifier la disponibilité d'un exemplaire

### Gestion des membres

- Ajouter un membre
- Modifier un membre
- Supprimer un membre
- Rechercher un membre

### Gestion des emprunts

- Enregistrer un emprunt
- Enregistrer un retour
- Consulter les emprunts en cours
- Historique des emprunts

### Statistiques

- Nombre total de livres
- Nombre total de membres
- Nombre d'emprunts
- Livres les plus empruntés
- Alertes de retard

---

## 🛠 Technologies utilisées

- Java 21
- Spring Boot 3.5.x
- Spring Data JPA
- Hibernate
- MySQL
- Maven

---

## 📂 Architecture

```text
src/main/java
│
├── controller
├── service
├── repository
├── model
├── dto
├── exception
└── config
```

L'application suit une architecture en couches :

- Controller → API REST
- Service → logique métier
- Repository → accès aux données
- DTO → transfert de données

---

## ⚙️ Installation

### 1. Cloner le projet

```bash
git clone https://github.com/VOTRE-USERNAME/kingo-biblio.git
cd kingo-biblio
```

### 2. Créer la base MySQL

```sql
CREATE DATABASE bibliotheque;
```

### 3. Configurer la connexion

Modifier :

```properties
src/main/resources/application.properties
```

Exemple :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bibliotheque
spring.datasource.username=root
spring.datasource.password=root

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 4. Lancer l'application

```bash
mvn clean install
mvn spring-boot:run
```

---

## 📡 API REST

### Livres

| Méthode | Endpoint |
|----------|----------|
| GET | /api/livres |
| GET | /api/livres/{id} |
| POST | /api/livres |
| PUT | /api/livres/{id} |
| DELETE | /api/livres/{id} |

### Membres

| Méthode | Endpoint |
|----------|----------|
| GET | /api/membres |
| POST | /api/membres |
| PUT | /api/membres/{id} |
| DELETE | /api/membres/{id} |

### Emprunts

| Méthode | Endpoint |
|----------|----------|
| POST | /api/emprunts |
| PUT | /api/emprunts/retour |
| GET | /api/emprunts |

---

## 📊 Statistiques disponibles

- Livres les plus empruntés
- Nombre d'emprunts sur une période
- Membres enregistrés
- Retards de retour

---

## 🔮 Améliorations futures

- Documentation Swagger/OpenAPI
- Authentification JWT
- Interface Web (Angular / React)
- Gestion des réservations
- Notifications automatiques

---

## 👨‍💻 Auteur : WOGLO Lincoln Shijo

Projet réalisé dans le cadre d'un portfolio Java / Spring Boot.
