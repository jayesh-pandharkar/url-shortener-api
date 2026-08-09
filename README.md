# 🔗 URL Shortener API

A production-oriented RESTful URL Shortener API built with Java and Spring Boot.

The application allows authenticated users to create, manage, and track shortened URLs. It uses JWT-based authentication, PostgreSQL for persistence, Spring Data JPA/Hibernate for database access, Docker/Docker Compose for containerized development, Swagger/OpenAPI for interactive API documentation, and Railway for cloud deployment.

---

## 🚀 Live Deployment

### Production API

https://url-shortener-api-production-91e8.up.railway.app

> The root URL does not represent a web frontend. This project is a REST API, so individual API endpoints should be accessed using HTTP methods such as GET, POST, PUT, and DELETE.

### Example Short URL

https://url-shortener-api-production-91e8.up.railway.app/{shortCode}

---

## 📌 Project Overview

Traditional URLs can be long and difficult to share.

This project provides a backend service that converts long URLs into compact short codes.

Example:

```text
Original URL:
https://github.com/

        ↓

Short URL:
https://url-shortener-api-production-91e8.up.railway.app/BmdLTM
```

When a user accesses the short URL, the application:

1. Receives the short code.
2. Searches for the corresponding URL in PostgreSQL.
3. Validates that the URL exists.
4. Increments the click counter.
5. Redirects the user to the original URL.

---

## ✨ Features

### Authentication

- User registration
- User login
- JWT-based authentication
- Stateless authentication
- Protected REST endpoints
- Bearer token authorization

### URL Management

- Create shortened URLs
- Generate unique short codes
- Redirect short URLs to original URLs
- Retrieve URLs belonging to the authenticated user
- Update shortened URLs
- Delete shortened URLs
- Track URL click counts
- Store URL creation timestamps

### Backend Engineering

- RESTful API design
- Layered architecture
- Controller-Service-Repository pattern
- DTO-based request handling
- Request validation
- Global error handling
- PostgreSQL persistence
- JPA/Hibernate ORM
- JWT security
- Swagger/OpenAPI documentation

### DevOps

- Docker containerization
- Docker Compose
- PostgreSQL container
- Environment-based configuration
- Railway cloud deployment

---

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Java | Backend programming language |
| Spring Boot | REST API framework |
| Spring Web | HTTP/REST API development |
| Spring Security | Authentication and authorization |
| JWT | Stateless authentication |
| Spring Data JPA | Database abstraction |
| Hibernate | ORM |
| PostgreSQL | Relational database |
| Maven | Build and dependency management |
| Docker | Application containerization |
| Docker Compose | Multi-container local environment |
| Swagger / OpenAPI | Interactive API documentation |
| Postman | API testing |
| Railway | Cloud deployment |
| Git / GitHub | Version control |

---

## 🏗️ Architecture

The application follows a layered backend architecture.

```text
                    Client
                      |
                      | HTTP Request
                      v
              +-------------------+
              |    Controller     |
              +-------------------+
                      |
                      v
              +-------------------+
              |     Service       |
              +-------------------+
                      |
                      v
              +-------------------+
              |    Repository     |
              +-------------------+
                      |
                      v
              +-------------------+
              |    PostgreSQL     |
              +-------------------+
```

### Main Layers

#### Controller Layer

Responsible for:

- Receiving HTTP requests
- Validating request input
- Returning HTTP responses
- Mapping API endpoints

#### Service Layer

Responsible for:

- Business logic
- URL generation
- Authentication logic
- Ownership checks
- Click tracking
- Coordinating repository operations

#### Repository Layer

Responsible for:

- Database interaction
- CRUD operations
- Querying URL and user records

#### Security Layer

Responsible for:

- JWT generation
- JWT validation
- Authentication
- Authorization
- Protecting secured endpoints

---

## 🔐 Authentication Architecture

The application uses JWT-based stateless authentication.

```text
                User
                  |
                  | Register
                  v
       POST /api/users/register
                  |
                  v
             User stored
                  |
                  | Login
                  v
          POST /api/auth/login
                  |
                  v
             JWT Token
                  |
                  v
       Authorization: Bearer <JWT>
                  |
                  v
          Protected APIs
```

The server does not need to maintain a traditional server-side session for authenticated users.

---

## 🔗 URL Shortening Flow

```text
Client
  |
  | POST /api/urls
  | { "originalUrl": "https://github.com" }
  |
  v
ShortUrlController
  |
  v
ShortUrlService
  |
  | Generate unique short code
  |
  v
ShortUrlRepository
  |
  v
PostgreSQL
  |
  v
Short URL response
```

Example response:

```json
{
  "id": 1,
  "originalUrl": "https://github.com",
  "shortCode": "BmdLTM",
  "shortUrl": "http://localhost:8080/BmdLTM",
  "clickCount": 0,
  "createdAt": "2026-08-09T13:47:49.136869389",
  "expiresAt": null
}
```

---

## 🔄 URL Redirection Flow

When a user accesses:

```text
GET /BmdLTM
```

the application performs:

```text
GET /{shortCode}
       |
       v
Find short code
       |
       v
Check URL exists
       |
       v
Increment click count
       |
       v
HTTP Redirect
       |
       v
Original URL
```

Example:

```text
https://url-shortener-api-production-91e8.up.railway.app/BmdLTM

                    ↓

https://github.com/
```

---

# 📚 REST API

## Authentication Endpoints

### Register User

```http
POST /api/users/register
```

Example request:

```json
{
  "username": "alex1",
  "password": "password123"
}
```

### Login

```http
POST /api/auth/login
```

Example request:

```json
{
  "username": "alex1",
  "password": "password123"
}
```

The login endpoint returns a JWT token.

The token is supplied to protected endpoints using:

```http
Authorization: Bearer <JWT_TOKEN>
```

---

## URL Endpoints

### Create Short URL

```http
POST /api/urls
```

Authentication: Required

Headers:

```http
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json
```

Request:

```json
{
  "originalUrl": "https://github.com/"
}
```

Example response:

```json
{
  "id": 1,
  "originalUrl": "https://github.com/",
  "shortCode": "BmdLTM",
  "shortUrl": "http://localhost:8080/BmdLTM",
  "clickCount": 0,
  "createdAt": "2026-08-09T13:47:49.136869389",
  "expiresAt": null
}
```

### Redirect to Original URL

```http
GET /{shortCode}
```

Example:

```http
GET /BmdLTM
```

The server resolves the short code and redirects the client to the original URL.

### Get My URLs

```http
GET /api/urls/my
```

Authentication: Required

Header:

```http
Authorization: Bearer <JWT_TOKEN>
```

Returns shortened URLs belonging to the authenticated user.

### Update Short URL

```http
PUT /api/urls/{shortCode}
```

Authentication: Required

Example:

```http
PUT /api/urls/BmdLTM
```

### Delete Short URL

```http
DELETE /api/urls/{shortCode}
```

Authentication: Required

Example:

```http
DELETE /api/urls/BmdLTM
```

---

# 📖 Swagger / OpenAPI

The project includes interactive API documentation using Swagger/OpenAPI.

After starting the application locally:

### Swagger UI

http://localhost:8080/swagger-ui/index.html

### OpenAPI JSON

http://localhost:8080/v3/api-docs

Swagger provides an interactive interface for:

- Viewing available endpoints
- Inspecting request/response models
- Testing APIs
- Supplying JWT authorization
- Understanding API contracts

---

# 🧪 API Testing

The APIs were tested using both Postman and Swagger UI.

Recommended testing workflow:

```text
1. Register user
       ↓
2. Login
       ↓
3. Copy JWT
       ↓
4. Authorize Swagger / Postman
       ↓
5. Create short URL
       ↓
6. Access short URL
       ↓
7. Verify redirect
       ↓
8. Check click count
       ↓
9. Update URL
       ↓
10. Delete URL
```

---

# 🐳 Docker

The application is containerized using Docker.

The Docker setup contains:

```text
+-----------------------------+
|       Docker Compose        |
+--------------+--------------+
               |
       +-------+-------+
       |               |
       v               v
+-------------+   +-------------+
| Spring Boot |   | PostgreSQL  |
|     API     |   |  Database   |
+-------------+   +-------------+
       |               |
       +-------+-------+
               |
          Docker Network
```

---

## 🐳 Run with Docker Compose

### Prerequisites

Install:

- Docker Desktop
- Git

Verify Docker:

```bash
docker --version
```

Verify Docker Compose:

```bash
docker compose version
```

### Start the application

From the project root:

```bash
docker compose up --build
```

The command starts:

```text
Spring Boot API
PostgreSQL
```

The API is available at:

http://localhost:8080

Swagger:

http://localhost:8080/swagger-ui/index.html

### Run in background

```bash
docker compose up --build -d
```

### View logs

```bash
docker compose logs -f
```

Application logs:

```bash
docker compose logs -f api
```

### Stop containers

```bash
docker compose down
```

### Stop containers and remove database volume

```bash
docker compose down -v
```

> This removes the PostgreSQL Docker volume and therefore deletes the locally persisted database data.

---

# 💻 Run Locally Without Docker

## Prerequisites

- Java 25+
- PostgreSQL
- Git
- Maven Wrapper

Check Java:

```bash
java -version
```

---

## Database Configuration

Create a PostgreSQL database:

```text
url_shortener
```

Configure the database connection in:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/url_shortener
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

> Do not commit production passwords, JWT secrets, or other credentials to Git.

---

# ▶️ Run the Application

Using Maven Wrapper:

```bash
./mvnw spring-boot:run
```

Or build the project:

```bash
./mvnw clean package
```

Then run the generated JAR:

```bash
java -jar target/<application-name>.jar
```

---

# 🧱 Build Docker Image Manually

Build the application JAR:

```bash
./mvnw clean package -DskipTests
```

Build the Docker image:

```bash
docker build -t url-shortener-api .
```

Run the container:

```bash
docker run -p 8080:8080 url-shortener-api
```

---

# 🗄️ Database

The application uses PostgreSQL as its relational database.

Spring Data JPA and Hibernate are used for object-relational mapping.

```text
Entity
   ↓
Repository
   ↓
Hibernate
   ↓
JDBC
   ↓
PostgreSQL
```

The application uses database persistence for:

- Users
- Short URLs
- Original URLs
- Short codes
- Click counts
- Creation timestamps
- URL ownership

---

# 🔒 Security

The application uses Spring Security and JWT.

### Spring Security

Used to protect API endpoints and implement authentication/authorization.

### JWT

JWT tokens are issued after successful authentication.

Protected requests use:

```http
Authorization: Bearer <JWT_TOKEN>
```

### Stateless Authentication

The server does not rely on an HTTP session for API authentication.

---

# 🌎 Production Deployment

The backend has been deployed using Railway.

Production URL:

https://url-shortener-api-production-91e8.up.railway.app

Deployment architecture:

```text
                  Internet
                     |
                     v
             Railway Service
                     |
                     v
              Spring Boot API
                     |
                     v
                PostgreSQL
```

Production configuration uses environment variables for sensitive values such as:

```text
DATABASE_URL
DATABASE_USERNAME
DATABASE_PASSWORD
JWT_SECRET
JWT_EXPIRATION
```

Sensitive credentials are not committed to the repository.

---

# 🔧 Environment Variables

For local or production environments, sensitive configuration should be provided through environment variables.

Example:

```text
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
JWT_SECRET
JWT_EXPIRATION
```

Actual secret values should never be committed to GitHub.

---

# 📁 Project Structure

```text
url-shortener/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── ...
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│
├── Dockerfile
├── docker-compose.yml
├── .dockerignore
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

The Java source code follows a layered architecture with dedicated packages for controllers, services, repositories, security, validation, DTOs, entities, and supporting utilities.

---

# 📈 Engineering Highlights

This project demonstrates practical backend engineering skills including:

- Designing RESTful APIs with Spring Boot
- Implementing layered architecture
- Applying dependency injection
- Building DTO-based request/response handling
- Implementing JWT authentication
- Configuring Spring Security
- Using Spring Data JPA
- Working with Hibernate ORM
- Designing PostgreSQL persistence
- Implementing URL ownership
- Generating unique short codes
- Implementing HTTP redirects
- Tracking URL click counts
- Handling API errors
- Documenting APIs using OpenAPI
- Testing APIs using Postman and Swagger
- Containerizing applications using Docker
- Running multi-container applications with Docker Compose
- Managing environment-specific configuration
- Deploying a backend application to Railway

---

# 🎯 Key Backend Concepts Demonstrated

## REST API Design

The project follows standard HTTP semantics:

```text
POST    → Create
GET     → Retrieve
PUT     → Update
DELETE  → Delete
```

## Separation of Concerns

Business logic is separated from HTTP handling and persistence.

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

This makes the application easier to:

- Test
- Maintain
- Extend
- Debug

## Stateless Authentication

JWT allows clients to authenticate requests without maintaining traditional server-side HTTP sessions.

## Database Persistence

JPA/Hibernate handles object-relational mapping between Java entities and PostgreSQL tables.

## Containerization

Docker packages the application and its runtime environment into a reproducible container.

Docker Compose is used to run the application and PostgreSQL together.

---

# 🚀 Future Improvements

Potential future enhancements include:

- Redis caching for frequently accessed short URLs
- Rate limiting
- Custom aliases
- URL expiration management
- QR code generation
- Analytics dashboard
- Advanced click analytics
- IP/device/referrer analytics
- Refresh tokens
- Role-based authorization
- CI/CD with GitHub Actions
- Custom production domain
- Centralized logging
- Application monitoring
- Prometheus/Grafana metrics

---

# 📌 Project Status

```text
✅ REST API
✅ JWT Authentication
✅ PostgreSQL
✅ Spring Data JPA
✅ Hibernate
✅ URL Shortening
✅ URL Redirection
✅ Click Tracking
✅ CRUD Operations
✅ Swagger/OpenAPI
✅ Postman Testing
✅ Docker
✅ Docker Compose
✅ Railway Deployment
✅ Production API
```

---

# 👨‍💻 Author

**Jayesh Pandharkar**

GitHub:

https://github.com/jayesh-pandharkar
