# PokerLi API (Backend)

## Overview

This is the backend service for PokerLi, a real-time Agile Planning Poker platform. It is built with Java 21 and Spring Boot, providing RESTful endpoints, JWT authentication, WebSocket support, and PostgreSQL persistence.

---

## Features

- User registration, login, and guest access (JWT-secured)
- Sprint and user story management
- Real-time updates via WebSocket (STOMP)
- Role-based access: Admin, Member, Guest
- PostgreSQL database integration
- Internationalization-ready error handling
- API-first design for easy frontend integration

---

## Technologies Used

- Java 21
- Spring Boot 3.5
- Spring Security (JWT)
- Spring Data JPA (PostgreSQL)
- WebSocket (STOMP)
- Maven
- Lombok

---

## Getting Started

### Prerequisites
- Java 21+
- Maven
- PostgreSQL

### 1. Configure Environment
Copy the example properties and edit as needed:
```sh
cp src/main/resources/application.properties.example src/main/resources/application.properties
# Edit DB credentials and server.port
```

### 2. Start PostgreSQL
Create a database (e.g., `pokerLi_db`) and update your credentials in `application.properties`.

### 3. Build & Run
```sh
./mvnw spring-boot:run
# or
mvn spring-boot:run
```
The API will be available at `http://localhost:8000/api` (default).

---

## Usage Examples

### Register a User
```http
POST /auth/register
{
  "name": "Ahmad Aldali",
  "email": "ahmad@example.com",
  "password": "yourpassword"
}
```

### Login
```http
POST /auth/login
{
  "email": "ahmad@example.com",
  "password": "yourpassword"
}
```

### Create a Sprint
```http
POST /sprints
Authorization: Bearer <token>
{
  "name": "Sprint 1",
  "sequence": [1,2,3,5,8,13]
}
```

### WebSocket Endpoint
- Connect to `/ws` for real-time updates (see frontend for usage)

---

## Further Documentation
- [HELP.md](HELP.md) — Spring Boot, Maven, and API references
- See the main [README.md](../README.md) for project-wide info

---

## License
See [../LICENSE](../LICENSE)
