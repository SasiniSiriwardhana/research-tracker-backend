# 🔬 Research Project Tracker — Backend

A secure, RESTful Spring Boot backend application providing JWT-based authentication, role-based access control, and full CRUD operations for managing research projects, milestones, and documents.

---

## 📋 Project Overview

**CMJD Final Project — Back-End Development with Spring Boot**
Educational Institute: IJSE (Institute of Java and Software Engineering)
Student: Sasini Siriwardhana | Batch 111
Frontend: React + TypeScript SPA

---

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Spring Boot 3 | Application framework |
| Spring Security 6 | Authentication & authorization |
| JSON Web Tokens (JWT) | Stateless authentication |
| Spring Data JPA | ORM & database abstraction |
| Hibernate | JPA implementation |
| MySQL 8 | Relational database |
| Maven | Dependency management & build |
| Lombok | Boilerplate code reduction |
| Jakarta Bean Validation | Request validation |

---

## 🚀 Setup & Installation

### Prerequisites
- Java 17 or above
- Maven 3.8+
- MySQL 8.0+

### Steps

```bash
# 1. Clone the repository
git clone <repository-url>
cd Research-Tracker-Backend

# 2. Create the MySQL database
# (It will be auto-created if it doesn't exist, based on application.properties)
mysql -u root -p
CREATE DATABASE IF NOT EXISTS research_tracker_db;
exit;

# 3. (Optional) Load sample data
mysql -u root -p research_tracker_db < sample_data.sql

# 4. Update application.properties with your DB credentials
# Edit: src/main/resources/application.properties

# 5. Run the application
./mvnw spring-boot:run
```

The backend will start on `http://localhost:8081`.

---

## ⚙️ Configuration

Edit `src/main/resources/application.properties` to match your environment:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/research_tracker_db
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
jwt.secret=YOUR_JWT_SECRET
jwt.expiration=86400000
```

---

## 🔑 Default Test Accounts

After importing `sample_data.sql`, use these credentials:

| Role | Username | Password |
|---|---|---|
| **ADMIN** | `admin@rpt.lk` | `Admin@1234` |
| **PI** | `dr.jayawardena@rpt.lk` | `Password@123` |
| **MEMBER** | `nimal.dissanayake@rpt.lk` | `Password@123` |
| **VIEWER** | `observer@rpt.lk` | `Password@123` |

---

## 📁 Project Structure

```
src/main/java/lk/ijse/cmjd/research_tracker/
├── ResearchTrackerApplication.java   # Application entry point
├── config/
│   ├── SecurityConfig.java           # Spring Security & filter chain
│   └── ApplicationConfig.java        # Beans: PasswordEncoder, AuthManager
├── controller/
│   ├── AuthController.java           # /api/auth/login, /api/auth/signup
│   ├── ProjectController.java        # /api/projects/**
│   ├── MilestoneController.java      # /api/milestones/**, /api/projects/:id/milestones
│   ├── DocumentController.java       # /api/documents/**, /api/projects/:id/documents
│   └── UserController.java           # /api/users/** (ADMIN)
├── dto/
│   ├── request/                      # Request DTOs
│   └── response/                     # Response DTOs
├── entity/
│   ├── User.java
│   ├── Project.java
│   ├── Milestone.java
│   └── Document.java
├── exception/
│   └── GlobalExceptionHandler.java   # Centralized error handling
├── repository/
│   ├── UserRepository.java
│   ├── ProjectRepository.java
│   ├── MilestoneRepository.java
│   └── DocumentRepository.java
├── security/
│   ├── JwtService.java               # Token generation & validation
│   ├── JwtAuthFilter.java            # Request filter
│   └── UserDetailsServiceImpl.java   # Load user from DB
└── service/
    ├── AuthService.java
    ├── ProjectService.java
    ├── MilestoneService.java
    ├── DocumentService.java
    └── UserService.java
```

---

## 📡 API Endpoints

### Authentication (Public)
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/signup` | Register new user |
| POST | `/api/auth/login` | Login, returns JWT |

### Projects
| Method | Endpoint | Access |
|---|---|---|
| GET | `/api/projects` | All authenticated |
| GET | `/api/projects/{id}` | All authenticated |
| POST | `/api/projects` | ADMIN, PI |
| PUT | `/api/projects/{id}` | ADMIN, PI |
| PATCH | `/api/projects/{id}/status` | ADMIN, PI |
| DELETE | `/api/projects/{id}` | ADMIN only |

### Milestones
| Method | Endpoint | Access |
|---|---|---|
| GET | `/api/projects/{id}/milestones` | All authenticated |
| POST | `/api/projects/{id}/milestones` | ADMIN, PI, MEMBER |
| PUT | `/api/milestones/{id}` | ADMIN, PI, MEMBER |
| DELETE | `/api/milestones/{id}` | ADMIN, PI |

### Documents
| Method | Endpoint | Access |
|---|---|---|
| GET | `/api/projects/{id}/documents` | All authenticated |
| POST | `/api/projects/{id}/documents` | ADMIN, PI, MEMBER |
| DELETE | `/api/documents/{id}` | ADMIN, PI |

### Users (Admin Only)
| Method | Endpoint | Access |
|---|---|---|
| GET | `/api/users` | ADMIN |
| GET | `/api/users/{id}` | ADMIN |
| DELETE | `/api/users/{id}` | ADMIN |

---

## 🔒 Security Design

- Stateless JWT authentication (no sessions)
- BCrypt password hashing
- Role-based method-level authorization
- Token expiry: 24 hours (configurable)
- CORS configured for frontend at `http://localhost:3000`

---

## 🌿 Git Branching Strategy

```
main         ← stable, production-ready code
feat/*       ← individual feature branches
fix/*        ← bug fix branches
```

---

## 👩‍💻 Author

**Sasini Siriwardhana**
Batch 111 | CMJD Final Project — Institute of Java and Software Engineering (IJSE)
