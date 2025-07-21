# Sol&R: Your Modern Day Solar Watch

Are you tired of endlessly sifting through Google searches and cluttered web pages just to find out when the sun sets tonight or when solar noon hits for your upcoming trip? Sol&R is here to simplify your life. It's a modern solar watch designed to cut through the noise and provide you with the most essential solar event times: sunrise, sunset, and solar noon, for any city, on any given date.

Sol&R is built to ease the tracking of solar events, making it the go-to app for anyone who gets overwhelmed by excessive online searching. While still evolving, Sol&R already offers a subtle visual experience, reflecting the colors of the sky at specific times throughout the day. Our long-term vision is to empower average users to easily search, plan, save, and explore what they can expect the sky to look like, making Sol&R the ultimate resource for understanding the sun's movement and the sky's appearance.

## Getting Started

To get Sol&R up and running on your local machine, follow these steps.

### ⚠️ Docker Notice ⚠️
Docker setup is currently not functional. Please proceed with the manual environment setup instructions below.

### Environment Files

The application uses environment variables for configuration. Copy the provided `.env.example` files and rename them to `.env`:

```bash
# Root config
cp .env.example .env

# Backend config
cd backend
cp .env.example .env

# Frontend config
cd ../frontend
cp .env.example .env
```

**Important:** Edit each `.env` file with your local values (e.g., database credentials, JWT secret, API URLs).

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

## Tech Stack

Sol&R is built as a modern full-stack web application, leveraging robust technologies for both its backend and frontend, with a clear separation of concerns.

### Backend (Java/Spring Boot)
- **Framework**: Spring Boot 3.4.3
- **Language**: Java 17
- **Build Tool**: Maven
- **Core Dependencies**:
  - Spring Web: For building efficient RESTful APIs
  - Spring WebFlux: Enabling reactive programming for scalable operations
  - Spring Data JPA: Simplifying database interactions
  - Spring Security: Handling authentication and authorization
- **Database**:
  - PostgreSQL: Our chosen production database
  - H2 Database: Used for lightweight development and testing
- **Authentication**: JWT (JSON Web Tokens) with the JJWT library for secure, stateless authentication
- **Utilities**:
  - Lombok: Reduces boilerplate code, keeping our codebase clean
  - Jackson: For seamless JSON processing, including JSR310 support for modern date/time APIs
  - Spring Dotenv: Manages environment variables securely

### Frontend (React)
- **Framework**: React 18.2.0
- **Language**: JavaScript/TypeScript
- **Build Tool**: Vite 6.3.5 for a fast development experience
- **Routing**: React Router DOM 7.6.1 for intuitive navigation
- **Styling**:
  - Tailwind CSS 4.1.11: For utility-first CSS
  - Emotion: A powerful CSS-in-JS library
- **UI Components**: Material-UI Date Pickers enhance user interaction
- **Date Handling**: Day.js 1.11.13 for efficient date and time manipulation

### Development Tools
- **Code Quality**: ESLint with React plugins to maintain high code standards
- **Code Formatting**: Prettier with the Tailwind CSS plugin ensures consistent code style
- **CSS Processing**: PostCSS with Autoprefixer for broad browser compatibility
- **Testing**: Spring Boot Test is used for comprehensive backend testing

### Architecture
Sol&R follows a full-stack web application pattern, clearly separating the backend and frontend. It provides RESTful services with reactive programming support. Authentication is handled via JWT-based stateless authentication, and JPA/Hibernate ORM manages interactions with the PostgreSQL database. Frontend and backend communicate seamlessly via HTTP REST APIs.

> **Note:** While listed, MapTiler SDK, MapLibre GL, MapTiler Geocoding Control, and OGL (3D Graphics) are planned integrations and not yet actively implemented.

## External APIs

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

Feel free to save this content as `README.md` in your project's root directory. Let me know if you need any further adjustments or if you decide to add the screenshots later!
