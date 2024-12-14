# Mini-Projet Architecture de Services

## Objectif :

Créer une application axée microservices en utilisant SOAP et REST

## Installation

```
mvn compile 
```

Une fois le projet compilé, lancer les différents services SOAP et REST via `Spring Boot` :

```
mvn spring-boot:run -pl rest-service
```


```
mvn spring-boot:run -pl soap-service
```

Vérifier le bon fonctionnement en accédant à `localhost:8080/hello` pour REST et `localhost:8081/ws` pour SOAP.



# Specifications

### **1. Objectifs de l'application**
- **Aider les personnes en besoin** : Faciliter la publication de demandes d’aide par des personnes en situation de vulnérabilité.
- **Mobiliser des volontaires** : Permettre aux volontaires de répondre à des demandes d’aide ou de proposer une aide spontanée.
- **Gérer l’administration** : Faciliter l’administration et le suivi des demandes.

### **2. REST Microservices**

#### **2.1 User Service**
- **Features:**
  - Account creation for requesters, volunteers, and administrators.
  - User profile management (modification and deletion).
  - Authentication and role management (requester, volunteer, administrator).
- **Main Endpoints:**
  - `POST /users`: Create a user.
  - `GET /users/{id}`: View a user's profile.
  - `PUT /users/{id}`: Update a user.
  - `DELETE /users/{id}`: Delete a user.

#### **2.2 Request Service**
- **Features:**
  - Creation, validation, and management of help requests.
  - Management of request statuses: Pending, validated, rejected, completed.
  - Viewing and filtering requests by status.
- **Main Endpoints:**
  - `POST /requests`: Create a request.
  - `GET /requests`: List of requests (with possible filters).
  - `GET /requests/{id}`: View a specific request.
  - `PUT /requests/{id}`: Update the status or details of a request.
  - `DELETE /requests/{id}`: Delete a request.

#### **2.3 Volunteer Service**
- **Features:**
  - Viewing available requests.
  - Offering help for a specific request.
  - Posting spontaneous help offers.
- **Main Endpoints:**
  - `GET /volunteers/requests`: List of available requests.
  - `POST /volunteers/{id}/help`: Respond to a specific request.
  - `POST /volunteers/spontaneous`: Post a spontaneous help offer.

#### **2.4 Feedback Service**
- **Features:**
  - Recording and managing feedback after a request is completed.
  - Viewing feedback associated with a request or user.
- **Main Endpoints:**
  - `POST /feedbacks`: Add feedback.
  - `GET /feedbacks`: View feedback (with possible filters).

#### **2.5 Administration Service**
- **Features:**
  - Validation or rejection of help requests.
  - Management of rejection reasons.
  - Viewing global statistics (e.g., number of completed requests).
- **Main Endpoints:**
  - `GET /admin/requests`: List of requests to be validated.
  - `PUT /admin/requests/{id}/validate`: Validate a request.
  - `PUT /admin/requests/{id}/reject`: Reject a request with justification.

### **3. Architecture technique**
- **Communication :** Chaque microservice expose une API REST avec des endpoints bien définis.
- **Base de données :** Chaque microservice possède sa propre base de données pour favoriser la décentralisation (database-per-service).
- **Authentification :** Utilisation de JWT pour sécuriser les communications.
- **Load Balancing et Gateway :** Utilisation d’un API Gateway pour la gestion des requêtes et la sécurité.
- **Monitoring :** Intégration de services comme Prometheus et Grafana pour le suivi des performances.



### **4. Avantages de cette architecture**
- **Scalabilité** : Les microservices peuvent être déployés et mis à l'échelle individuellement.
- **Flexibilité** : Possibilité d’utiliser différentes technologies pour chaque service.
- **Maintenance** : Une meilleure isolation des fonctionnalités simplifie le débogage et les mises à jour.
