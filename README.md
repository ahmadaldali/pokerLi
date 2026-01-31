# PokerLi: Agile Planning Poker Platform

## Table of Contents
- [Description](#description)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
  - [Backend Setup (Spring Boot)](#backend-setup-spring-boot)
  - [Frontend Setup (SvelteKit)](#frontend-setup-sveltekit)
- [Support & Documentation](#support--documentation)
- [Maintainers](#maintainers)

---

## Description

**PokerLi** is a modern, open-source Agile Planning Poker platform. It enables distributed teams to estimate user stories collaboratively, using real-time voting, sprints, and user management. The project consists of a Spring Boot backend API and a SvelteKit/Tailwind frontend.

---

## Features

- **Real-time Planning Poker**: Estimate stories in sprints with live WebSocket updates
- **User Authentication**: Register, login, guest access, JWT-secured endpoints
- **Sprint & User Story Management**: Create, join, and manage sprints and user stories
- **Role-based Access**: Admin, Member, Guest roles
- **Internationalization**: English & Romanian support
- **RESTful API**: Well-structured endpoints for all core resources
- **Responsive UI**: Modern, mobile-friendly SvelteKit frontend

---

## Technologies Used

### Backend (api/)
- Java 21
- Spring Boot 3.5
- Spring Security (JWT)
- Spring Data JPA (PostgreSQL)
- WebSocket (STOMP)
- Maven

### Frontend (frontend/)
- SvelteKit
- TypeScript
- TailwindCSS
- Vite
- i18n (typesafe-i18n)
- WebSocket (SockJS)

---

## Getting Started

### Prerequisites
- [Node.js](https://nodejs.org/) (v18+ recommended)
- [Java 21+](https://adoptium.net/)
- [Maven](https://maven.apache.org/)
- [PostgreSQL](https://www.postgresql.org/)

### 1. Clone the Repository
```sh
git clone https://github.com/ahmadaldali/pokerLi
cd pokerLi
```

### Backend Setup (Spring Boot)
1. Copy and configure environment:
   ```sh
   cd api
   cp src/main/resources/application.properties.example src/main/resources/application.properties
   # Edit DB credentials and server.port as needed
   ```
2. Start PostgreSQL and create a database (e.g., `pokerLi_db`).
3. Build and run the API:
   ```sh
   ./mvnw spring-boot:run
   # or
   mvn spring-boot:run
   ```
   The API runs by default on `http://localhost:8000/api`.

### Frontend Setup (SvelteKit)
1. Install dependencies: `from root directory`
   ```sh
   cd frontend
   npm install
   cp .env.example .env
   ```
2. Start the dev server:
   ```sh
   npm run dev
   ```
   The app runs on `http://localhost:5173`.

---

## Support & Documentation

- **API Reference**: See [api/README.md](api/README.md) and [api/HELP.md](api/HELP.md)
- **Frontend Docs**: See [frontend/README.md](frontend/README.md)
- **Issue Tracker**: Use GitHub Issues
---

## Maintainers

- **Ahmad Aldali** — [GitHub](https://github.com/ahmadaldali)

---

**License:** See [LICENSE](LICENSE)