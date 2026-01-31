# PokerLi Frontend (SvelteKit)

## Overview

This is the frontend for PokerLi, a real-time Agile Planning Poker platform. Built with SvelteKit, TypeScript, and TailwindCSS, it provides a modern, responsive UI for collaborative estimation, sprint management, and real-time updates.

---

## Features

- Responsive, mobile-friendly UI
- Real-time updates via WebSocket (SockJS)
- User authentication (register, login, guest)
- Sprint and user story management
- Role-based access (Admin, Member, Guest)
- Internationalization (English, Romanian)
- Type-safe API integration

---

## Technologies Used

- SvelteKit
- TypeScript
- TailwindCSS
- Vite
- typesafe-i18n
- SockJS (WebSocket)

---

## Getting Started

### Prerequisites
- Node.js v18+

### 1. Install Dependencies
```sh
npm install
```

### 2. Configure Environment (Optional)
- Copy `.env.example` to `.env` and adjust API URLs if needed.

### 3. Start the Development Server
```sh
npm run dev
```
- The app runs at `http://localhost:5173` by default.

### 4. Build for Production
```sh
npm run build
npm run preview
```

---

## Usage Examples

- Register, login, or join as guest
- Create or join sprints
- Vote on user stories in real time
- Switch language (EN/RO)

---

## Project Structure

- `src/routes/` — SvelteKit pages and layouts
- `src/lib/` — Shared client/server code, API, and assets
- `src/i18n/` — Internationalization setup and translations
- `static/` — Static assets (fonts, robots.txt, etc.)

---

## Further Documentation
- See the main [README.md](../README.md) for project-wide info
- API endpoints: [../api/README.md](../api/README.md)

---

## License
See [../LICENSE](../LICENSE)
