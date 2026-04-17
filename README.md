# Tiny Corp Laptop Tracker

Tiny Corp Laptop Tracker is a Spring Boot REST application for managing users, devices, assignment history, and management reports.

## Features
- JWT-based authentication (`POST /api/v1/auth/login`)
- User management (create, disable, list)
- Device management (create, get, list)
- Assignment lifecycle (assign, return)
- Manager-only reports
- Validation + global exception handling
- Pagination support for list/report endpoints
- OpenAPI/Swagger docs
- Service-layer and HTTP request logging

## Tech Stack
- Java 17
- Spring Boot 3.4.x
- Spring Web
- Spring Data JPA (H2 in-memory DB)
- Spring Security + JWT (jjwt)
- Jakarta Bean Validation
- Springdoc OpenAPI
- JUnit 5 + Spring Boot Test + MockMvc

## Project Structure
- `src/main/java/com/tinycorp/laptoptracker/controller` - REST controllers
- `src/main/java/com/tinycorp/laptoptracker/service` - service interfaces + business logic
- `src/main/java/com/tinycorp/laptoptracker/repository` - JPA repositories
- `src/main/java/com/tinycorp/laptoptracker/domain` - entities and enums
- `src/main/java/com/tinycorp/laptoptracker/security` - JWT auth + filter
- `src/main/java/com/tinycorp/laptoptracker/config` - security, OpenAPI, logging, seed config
- `src/test/java/com/tinycorp/laptoptracker` - service and integration tests

## How to Run
### 1. Build and test
```bash
mvn clean install
```

### 2. Start application
```bash
mvn spring-boot:run
```

Application base URL:
- `http://localhost:8080`

## Docker
### Prerequisites
- Docker Desktop installed and running
- Docker daemon healthy (`docker info` should succeed)

### Build image
```bash
docker build -t tinycorp/laptop-tracker:latest .
```

### Run container
```bash
docker run --rm -p 8080:8080 --name tinycorp-laptop-tracker tinycorp/laptop-tracker:latest
```

### Run container in detached mode
```bash
docker run -d --rm -p 8080:8080 --name tinycorp-laptop-tracker tinycorp/laptop-tracker:latest
```

### Run with Docker Compose
```bash
docker compose up --build
```

### Run with Docker Compose (detached)
```bash
docker compose up --build -d
```

### Stop container
If started with `docker run`:
```bash
docker stop tinycorp-laptop-tracker
```

If started with Compose:
```bash
docker compose down
```

### View logs
If started with `docker run`:
```bash
docker logs -f tinycorp-laptop-tracker
```

If started with Compose:
```bash
docker compose logs -f
```

### Verify container is running
```bash
docker ps
```

Dockerized app URLs:
- `http://localhost:8080/`
- `http://localhost:8080/swagger-ui/index.html`

### Quick API test after container start
Login:
```bash
curl -X POST http://localhost:8080/api/v1/auth/login ^
  -H "Content-Type: application/json" ^
  -d "{\"username\":\"manager\",\"password\":\"manager123\"}"
```

### Common troubleshooting
- Error: `failed to connect to the docker API ... dockerDesktopLinuxEngine`
  - Start Docker Desktop and wait until engine status is `Running`.
- Port conflict on `8080`
  - Stop the process using that port or map another port:
  - `docker run --rm -p 8081:8080 --name tinycorp-laptop-tracker tinycorp/laptop-tracker:latest`

## Default Seeded Users
- `manager / manager123` (`MANAGER`)
- `employee / employee123` (`EMPLOYEE`)

## Authentication
### Login
```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "username": "manager",
  "password": "manager123"
}
```

Response:
```json
{
  "token": "<jwt-token>"
}
```

Use on protected APIs:
```http
Authorization: Bearer <jwt-token>
```

## Pagination
Supported on:
- `GET /api/v1/users`
- `GET /api/v1/devices`
- `GET /api/v1/reports/user-devices`
- `GET /api/v1/reports/eol-devices`

Query params:
- `page` (default `0`, min `0`)
- `size` (default `10`, min `1`)

Example:
```http
GET /api/v1/users?page=0&size=5
Authorization: Bearer <jwt-token>
```

Paginated response shape:
```json
{
  "content": [],
  "page": 0,
  "size": 10,
  "totalElements": 0,
  "totalPages": 0,
  "first": true,
  "last": true
}
```

## API Endpoints
### Auth
- `POST /api/v1/auth/login`

### Users
- `GET /api/v1/users?page=0&size=10`
- `POST /api/v1/users`
- `PUT /api/v1/users/{userId}/disable`

### Devices
- `GET /api/v1/devices?page=0&size=10`
- `GET /api/v1/devices/{id}`
- `POST /api/v1/devices`

### Assignments
- `POST /api/v1/assignments`
- `PUT /api/v1/assignments/{assignmentId}/return`

### Reports (`MANAGER` only)
- `GET /api/v1/reports/average-device-age`
- `GET /api/v1/reports/user-devices?page=0&size=10`
- `GET /api/v1/reports/eol-devices?page=0&size=10`

## Example API Calls
### Create user
```http
POST /api/v1/users
Content-Type: application/json
Authorization: Bearer <jwt-token>

{
  "username": "alice",
  "password": "alice123",
  "role": "EMPLOYEE"
}
```

### Create device
```http
POST /api/v1/devices
Content-Type: application/json
Authorization: Bearer <jwt-token>

{
  "brand": "HP",
  "model": "EliteBook 840",
  "cpu": "Intel i7",
  "ram": 16,
  "manufactureYear": 2024
}
```

### Assign device
```http
POST /api/v1/assignments
Content-Type: application/json
Authorization: Bearer <jwt-token>

{
  "userId": 2,
  "deviceId": 1,
  "assignedDate": "2026-04-17"
}
```

### Return assigned device
```http
PUT /api/v1/assignments/1/return
Content-Type: application/json
Authorization: Bearer <jwt-token>

{
  "returnedDate": "2026-04-20"
}
```

## OpenAPI Specification
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

The OpenAPI configuration includes bearer auth metadata for JWT-protected endpoints.

## Logs
Application logs are generated to:
- `logs/laptop-tracker.log`

Logged details include:
- Service-layer actions (business operations)
- HTTP request traces via request logging filter

## H2 Database Console
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:laptoptracker`
- Username: `sa`
- Password: *(empty)*

## Testing
Run all tests:
```bash
mvn test
```

Current coverage includes:
- Service tests for core user/assignment business rules
- Integration tests for authentication and role-based report access

## Business Rules Enforced
- Disabled users cannot login
- A user can have multiple active device assignments
- A device cannot have more than one active assignment
- Assignment fails when user/device state is invalid
- Report endpoints are manager-only

## UI for Quick Login Testing
A simple browser UI is available at:
- `http://localhost:8080/`

It supports:
- Login with manager/employee
- Quick test calls for `/api/v1/users` and `/api/v1/reports/average-device-age`
