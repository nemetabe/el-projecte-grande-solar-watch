### Sol&R: Your Modern Day Solar Watch

Are you tired of endlessly sifting through Google searches and cluttered web pages just to find out when the sun sets tonight or when solar noon hits for your upcoming trip? Sol&R is here to simplify your life. It's a modern solar watch designed to cut through the noise and provide you with the most essential solar event times: sunrise, sunset, and solar noon, for any city, on any given date.

Sol&R is built to ease the tracking of solar events, making it the go-to app for anyone who gets overwhelmed by excessive online searching. While still evolving, Sol&R already offers a subtle visual experience, reflecting the colors of the sky at specific times throughout the day. Our long-term vision is to empower average users to easily search, plan, save, and explore what they can expect the sky to look like, making Sol&R the ultimate resource for understanding the sun's movement and the sky's appearance.

Here is your modified README setup section, rewritten to use Docker instead of manual .env export and manual backend/frontend startup. Only the setup parts are changed as requested.

⸻

## Sol&R: Your Modern Day Solar Watch

# Getting Started

To get Sol&R running on your local machine using Docker, follow these steps.

⸻

🐳 Docker Setup (Recommended)

Sol&R is fully containerized using Docker Compose. This will automatically start:
	•	PostgreSQL database
	•	Spring Boot backend
	•	React frontend
	•	Nginx reverse proxy

No manual environment exporting or local PostgreSQL installation is required.

⸻

# Prerequisites

Make sure you have installed:
	•	Docker
	•	Docker Compose

Verify installation:
```bash
docker --version
docker compose version
```

⸻

## Environment Files

Copy the example environment files:

# Root config
```bash
cp .env.example .env
```

# Backend config
```bash
cd backend
cp src/.env.example src/.env
```

# Frontend config
```bash
cd ../frontend
cp .env.example .env
```


⸻

# Backend Environment Configuration (Docker)

Edit:

backend/src/.env

Use Docker service name db as host:

```
DB_URL=jdbc:postgresql://db:5432/solarwatchdb
DB_USERNAME=postgres
DB_PASSWORD=postgres

JWT_SECRET=your_jwt_secret_key
JWT_EXP=3600000
```
Important:
db is the Docker Compose service name. Docker automatically resolves it.

⸻

# Build and Start the Application

From the project root directory:
```bash
docker compose up --build
```

This will:
	•	Build backend image
	•	Build frontend image
	•	Start PostgreSQL
	•	Start Spring Boot API
	•	Start Nginx
	•	Connect everything automatically

⸻

## Access the Application

Once running, access:

# Frontend:

```bash
http://localhost
```

# Backend API:

```bash
http://localhost:8080/api/solar
```

Example:

```bash
curl "http://localhost:8080/api/solar?city=Budapest"
```

⸻

# Stop the Application
```bash
docker compose down
```


⸻

# Stop and Remove Everything (including database)
```bash
docker compose down -v
```


⸻

# Rebuild After Code Changes
```bash
docker compose up --build
```


⸻

# View Logs

All services:
```bash
docker compose logs -f
```

Specific service:
```bash
docker compose logs -f api
```

or
```bash 
docker compose logs -f db
```


⸻

## Docker Services Overview

Service	/Container Name	    /Port	  /Description
PostgreSQL	/solarwatch-db	/5432	/Database
Backend API	/solarwatch-api	/8080	/Spring Boot API
Frontend	/solarwatch-frontend	/internal	/React app
Nginx	/nginx	/80	/Reverse proxy


⸻

# Database Notes

Docker automatically creates:

Database: solarwatchdb
Username: postgres
Password: postgres
Host: db
Port: 5432

No manual database setup required.

⸻

First Startup May Take Time

The first run may take 1–3 minutes due to:
	•	Maven dependency download
	•	npm dependency install
	•	Docker image build

Subsequent runs will be much faster.

⸻

# Development Workflow

Start:
```bash
docker compose up
```

Stop:
```bash
docker compose down
```

Rebuild after code changes:
```bash
docker compose up --build
```

### ⚠️ Security Guidelines ⚠️

- Never commit `.env` files to version control — they are already included in `.gitignore`.
- If secrets are accidentally pushed:
  - Remove them from Git history
  - Rotate keys immediately
  - Generate new credentials

### Database Setup

Sol&R uses PostgreSQL. Please ensure you have PostgreSQL installed.

1. Connect to your PostgreSQL server
2. Create the database:

```sql
CREATE DATABASE solarwatchdb;
```

### Backend Setup

1. Navigate to the backend folder:

```bash
cd backend
```

2. Ensure your `.env` file in the backend directory contains the following configuration:

```env
DB_USERNAME=your_postgres_username
DB_PASSWORD=your_postgres_password
DB_URL=jdbc:postgresql://localhost:5432/solarwatchdb
SECRET_KEY=your_jwt_secret_key
EXPIRATION=6800000
```

3. Build and run the backend application:

```bash
./mvnw clean install
./export $(cat src/.env | xargs)
./mvnw spring-boot:run
```

The backend server will be running at `http://localhost:8080`.

### Frontend Setup

1. Navigate to the frontend application directory:

```bash
cd frontend
```

2. Install dependencies and start the development server:

```bash
npm install
npm run dev
```

The frontend application will be running at `http://localhost:5173`.

## Future Plans

Sol&R is continuously evolving, and we have exciting plans to enhance its functionality and user experience. Our aim is to make Sol&R the ultimate go-to application for anyone seeking information about the sun's movement and the sky's appearance.

Here's what's on the horizon:

- **Advanced Sky Visualizations**: Building on our current subtle sky color feature, we plan to implement accurate gradients of the sky for any location and date.

- **Comprehensive Celestial Data**: We envision expanding beyond sunrise, sunset, and solar noon to include more detailed solar data, such as twilight phases, and potentially integrating moon phases for a more complete celestial overview.

- **User Management & Personalization**: Future iterations will include user accounts, enabling features like saving favorite locations, and personalized notifications for specific solar occurrences.

- **Full Dockerization**: While currently under development, we are committed to fully integrating Docker support for both development and production environments, making setup and deployment even smoother.

- **Map-Based Location Selection**: To enhance usability, we plan to implement an intuitive map-based interface for selecting locations, offering a more visual and precise way to find solar information for any point on the globe.

- **Harmonic UI/UX Touch-Ups**: We'll continue to refine the user interface and experience, focusing on achieving a seamless, aesthetic, and visually pleasing look that complements the natural beauty of the sky's colors.

## 🛠 Tech Stack

### Backend (Java / Spring)

![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)  
**Spring Boot** `3.4.3`

![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)  
**Spring Framework (Web, WebFlux, Data JPA)**

![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white)  
**Spring Security**

![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)  
**PostgreSQL**

![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white)  
**JWT (JJWT)**

**Java** `17`  
**Maven**  
**H2 Database** (dev & testing)  
**Lombok**  
**Jackson (JSR310)**  
**Spring Dotenv**

---

### Frontend (React)

![Vite](https://img.shields.io/badge/Vite-B73BFE?style=for-the-badge&logo=vite&logoColor=FFD62E)  
**Vite** `6.3.5`

![React Router](https://img.shields.io/badge/React_Router-CA4245?style=for-the-badge&logo=react-router&logoColor=white)  
**React Router DOM** `7.6.1`

![Tailwind CSS](https://img.shields.io/badge/Tailwind_CSS-38B2AC?style=for-the-badge&logo=tailwind-css&logoColor=white)  
**Tailwind CSS** `4.1.11`

![Material UI](https://img.shields.io/badge/Material%20UI-007FFF?style=for-the-badge&logo=mui&logoColor=white)  
**Material UI Date Pickers**

**React** `18.2.0`  
**TypeScript / JavaScript**  
**Emotion (CSS-in-JS)**  
**Day.js** `1.11.13`

---

### Development & Tooling

![Docker Compose](https://img.shields.io/badge/Docker%20Compose-2496ED?style=for-the-badge&logo=docker&logoColor=white)  
**Docker Compose**

![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)  
**GitHub**

![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ_IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)  
**IntelliJ IDEA**

![VSCode](https://img.shields.io/badge/VSCode-0078D4?style=for-the-badge&logo=visual%20studio%20code&logoColor=white)  
**VS Code**

![WebStorm](https://img.shields.io/badge/WebStorm-000000?style=for-the-badge&logo=WebStorm&logoColor=white)  
**WebStorm**

![Vitest](https://img.shields.io/badge/Vitest-%236E9F18?style=for-the-badge&logo=Vitest&logoColor=%23fcd703)  
**Vitest**

**ESLint**  
**Prettier (Tailwind plugin)**  
**PostCSS + Autoprefixer**  
**Spring Boot Test**

---

### Planned Integrations

**MapTiler SDK**  
**MapLibre GL**  
**MapTiler Geocoding Control**  
**OGL (3D Graphics)**

Sol&R currently integrates with the following external APIs to provide accurate solar and geographical data:

- **OpenWeatherMap Geocoding API**: Used for converting city names into geographical coordinates (latitude and longitude), which are essential for precise solar calculations.
  - [Documentation](https://openweathermap.org/api/geocoding-api)

- **Sunrise-Sunset.org API**: Provides the core functionality for fetching accurate sunrise, sunset, and solar noon times for specific geographical coordinates and dates.
  - [Documentation](https://sunrise-sunset.org/api)

## API Endpoints

Sol&R provides a straightforward API endpoint to retrieve solar event times for a given location and date. This endpoint is designed for simplicity, allowing you to quickly get the data you need.

### Get Solar Events by City and Date

**Endpoint:** `GET /api/solar`

**Description:** Retrieves sunrise, sunset, and solar noon times for a specified city on a given date.

**Query Parameters:**

| Parameter | Type   | Required | Description |
|-----------|--------|----------|-------------|
| `city`    | string | No       | The name of the city for which to retrieve solar events. Defaults to "Budapest" if not provided. |
| `date`    | string | No       | The date for which to retrieve solar events, in YYYY-MM-DD format. Defaults to the current date if not provided. |

### How it works:
You can make a GET request to this endpoint, optionally including `city` and `date` as query parameters.

### Example Requests:

Get solar events for Budapest today (default behavior):
```bash
curl "http://localhost:8080/api/solar"
```

Get solar events for London on a specific date:
```bash
curl "http://localhost:8080/api/solar?city=London&date=2025-08-15"
```

Get solar events for Berlin today:
```bash
curl "http://localhost:8080/api/solar?city=Berlin"
```

### Example Response (JSON):

```json
{
  "city": "Budapest",
  "date": "2025-07-21",
  "sunrise": "05:01 AM",
  "sunset": "08:34 PM",
  "solarNoon": "01:47 PM"
}
```

> **Note:** The exact times in the example response are illustrative and would vary based on the actual date and location. The current date is July 21, 2025.

---
