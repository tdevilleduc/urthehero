# urthehero

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=urthehero&metric=coverage)](https://sonarcloud.io/dashboard?id=urthehero)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=urthehero&metric=sqale_index)](https://sonarcloud.io/dashboard?id=urthehero)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=urthehero&metric=alert_status)](https://sonarcloud.io/dashboard?id=urthehero)

A **choose-your-own-adventure** REST API backend. Players navigate through stories, make choices, fight enemies, and track their progression — all driven by a fully authenticated API.

## Tech stack

- **Language**: Kotlin
- **Framework**: Spring Boot 4.0.5 / Spring Cloud 2025.1.1
- **Security**: Spring Security 7 + JWT + RBAC (`ROLE_ADMIN` / `ROLE_USER`)
- **Database**: PostgreSQL
- **API docs**: SpringDoc / OpenAPI (Swagger UI)
- **Resilience**: Resilience4j (circuit breaker, retry)
- **Chaos testing**: Chaos Monkey for Spring Boot
- **Build**: Maven (Maven Wrapper included)
- **Containerization**: Docker / Docker Compose

## Project structure

```
urthehero/
├── back/               # Main REST API (Spring Boot)
├── config-server/      # Spring Cloud Config Server
└── docker-compose.yml  # Local environment (PostgreSQL + back)
```

### Domain

| Entity      | Description                                      |
|-------------|--------------------------------------------------|
| Story       | An interactive story a player can choose         |
| Page        | A story page with choices (next pages)           |
| NextPage    | A transition between pages                       |
| Progression | Tracks a player's current position in a story    |
| User        | An authenticated player                          |
| Enemy       | A monster/enemy a player can encounter           |
| Dice        | A dice roll mechanic                             |

## Prerequisites

- Java 21+
- Maven 3.8+ (or use `./mvnw`)
- Docker & Docker Compose

## Getting started

### 1. Start the database

```bash
docker compose up -d postgres
```

### 2. Build and run the backend

```bash
./mvnw -pl back spring-boot:run
```

The API is available at `http://localhost:8080`.

### 3. Run the full stack with Docker Compose

```bash
# Build the JAR first
./mvnw -pl back clean package -DskipTests

# Start all services
docker compose up -d
```

## API documentation

Once the application is running, the Swagger UI is available at:

```
http://localhost:8080/swagger-ui.html
```

### Authentication

All API endpoints require a JWT token. Authenticate first:

```bash
POST /authenticate
{
  "username": "...",
  "password": "..."
}
```

Use the returned token as a `Bearer` header or via the Swagger UI lock icon.

### Authorization

Two roles are supported:

| Role         | Permissions                                      |
|--------------|--------------------------------------------------|
| `ROLE_ADMIN` | Full access — read and write (PUT/POST/DELETE)   |
| `ROLE_USER`  | Read-only access                                 |

### Available endpoints

| Prefix              | Description              |
|---------------------|--------------------------|
| `/authenticate/**`  | Login / token generation |
| `/api/story/**`     | Story management         |
| `/api/page/**`      | Page management          |
| `/api/user/**`      | User management          |
| `/api/progression/**` | Player progression     |
| `/api/enemy/**`     | Enemy / monster CRUD     |
| `/api/dice/**`      | Dice roll                |

## Running tests

```bash
./mvnw test
```

Integration tests use [Testcontainers](https://testcontainers.com/) and require a running Docker daemon.

## CI/CD

The pipeline (`.gitlab-ci.yml`) runs four stages:

| Stage     | Description                                      |
|-----------|--------------------------------------------------|
| `build`   | Compile the project                              |
| `test`    | Run tests + SonarCloud analysis                  |
| `quality` | CodeClimate analysis (merge requests and main branches) |
| `package` | Build and push Docker image (main branches only) |

## Changelog

See [CHANGELOG.md](CHANGELOG.md) for the full release history.
