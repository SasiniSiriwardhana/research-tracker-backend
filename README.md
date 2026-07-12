# Research Project Tracker System

A comprehensive backend API for managing academic research projects within an educational institute. Built with Spring Boot, Spring Security (JWT), Spring Data JPA, and MySQL.

---

## 📋 Table of Contents

- [Overview](#overview)
- [Technology Stack](#technology-stack)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Authentication](#authentication)
- [Database Schema](#database-schema)
- [Sample Data](#sample-data)
- [Testing with Postman](#testing-with-postman)

---

## 📖 Overview

The Research Project Tracker System enables educational institutes to:

- **Create and manage** academic research projects
- **Track milestones** and deliverables for each project
- **Upload and organize** research documents
- **Control access** with role-based authorization (Admin, PI, Member, Viewer)
- **Authenticate securely** using JWT tokens with BCrypt password hashing

---

## 🛠️ Technology Stack

| Technology        | Version   | Purpose                          |
|-------------------|-----------|----------------------------------|
| Java              | 21        | Programming Language             |
| Spring Boot       | 3.4.x     | Application Framework            |
| Spring Web        | -         | REST API Development             |
| Spring Data JPA   | -         | Data Persistence (ORM)           |
| Spring Security   | -         | Authentication & Authorization   |
| JWT (jjwt)        | 0.12.6    | Token-based Authentication       |
| MySQL             | 8.x       | Relational Database              |
| Maven             | -         | Build Tool                       |
| Lombok            | -         | Boilerplate Code Reduction       |
| Jakarta Validation| -         | Input Validation                 |

---

## 🏗️ Architecture

```
lk.ijse.cmjd.research_tracker
│
├── common/             # Shared utilities (ApiResponse wrapper)
├── config/             # Security & application configuration
├── controller/         # REST API controllers
├── dto/                # Data Transfer Objects
├── entity/             # JPA entities
├── enums/              # Enumerations (UserRole, Status)
├── exception/          # Custom exceptions & global handler
├── repository/         # Spring Data JPA repositories
├── security/           # JWT utilities, filters, entry point
└── service/
    └── impl/           # Service interfaces & implementations
```

---

## 🚀 Getting Started

### Prerequisites

- **Java 21** (JDK)
- **MySQL 8.x** running on `localhost:3306`
- **Maven 3.9+**
- **Postman** (for API testing)

### Setup Steps

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd research-tracker
   ```

2. **Create the MySQL database:**
   ```sql
   CREATE DATABASE research_tracker_db;
   ```

3. **Configure database credentials** in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.username=root
   spring.datasource.password=root
   ```

4. **Build and run the application:**
   ```bash
   ./mvnw spring-boot:run
   ```

5. **The API will be available at:** `http://localhost:8081`

6. **(Optional) Load sample data:**
   Execute `src/main/resources/sample-data.sql` in your MySQL client.

---

## 📡 API Endpoints

### Authentication

| Method | Endpoint             | Description              | Access   |
|--------|----------------------|--------------------------|----------|
| POST   | `/api/auth/signup`   | Register new user        | Public   |
| POST   | `/api/auth/login`    | Login & get JWT token    | Public   |

### Projects

| Method | Endpoint                       | Description              | Access       |
|--------|--------------------------------|--------------------------|--------------|
| GET    | `/api/projects`                | List all projects        | Authenticated|
| GET    | `/api/projects/{id}`           | View project details     | Authenticated|
| POST   | `/api/projects`                | Create new project       | ADMIN, PI    |
| PUT    | `/api/projects/{id}`           | Update project           | ADMIN, PI    |
| PATCH  | `/api/projects/{id}/status`    | Update project status    | ADMIN, PI    |
| DELETE | `/api/projects/{id}`           | Delete project           | ADMIN        |

### Milestones

| Method | Endpoint                              | Description              | Access             |
|--------|---------------------------------------|--------------------------|--------------------|
| GET    | `/api/projects/{id}/milestones`       | List project milestones  | Authenticated      |
| POST   | `/api/projects/{id}/milestones`       | Add milestone            | ADMIN, PI, MEMBER  |
| PUT    | `/api/milestones/{id}`                | Update milestone         | ADMIN, PI, MEMBER  |
| DELETE | `/api/milestones/{id}`                | Delete milestone         | ADMIN, PI          |

### Documents

| Method | Endpoint                              | Description              | Access             |
|--------|---------------------------------------|--------------------------|--------------------|
| GET    | `/api/projects/{id}/documents`        | List project documents   | Authenticated      |
| POST   | `/api/projects/{id}/documents`        | Upload document          | ADMIN, PI, MEMBER  |
| DELETE | `/api/documents/{id}`                 | Delete document          | ADMIN, PI          |

### Users

| Method | Endpoint            | Description          | Access   |
|--------|---------------------|----------------------|----------|
| GET    | `/api/users`        | List all users       | ADMIN    |
| GET    | `/api/users/{id}`   | View user profile    | Authenticated |
| DELETE | `/api/users/{id}`   | Delete user          | ADMIN    |

---

## 🔐 Authentication

### How It Works

1. **Register** a new account via `POST /api/auth/signup`
2. **Login** via `POST /api/auth/login` to receive a JWT token
3. **Include the token** in all subsequent requests:
   ```
   Authorization: Bearer <your-jwt-token>
   ```

### Roles & Permissions

| Role     | Permissions                                                    |
|----------|----------------------------------------------------------------|
| `ADMIN`  | Full system access — manage users, projects, milestones, docs  |
| `PI`     | Manage own projects, milestones, and documents                 |
| `MEMBER` | Create/update milestones, upload documents                     |
| `VIEWER` | Read-only access to project data                               |

### Default Registration Role

New users are assigned the `MEMBER` role by default.

---

## 🗄️ Database Schema

### Entity Relationship Diagram

```
┌─────────┐       ┌───────────┐       ┌────────────┐
│  User   │◄──────│  Project  │◄──────│  Milestone │
│         │  PI   │           │Project│            │
└────┬────┘       └─────┬─────┘       └────────────┘
     │                  │
     │ uploadedBy       │ Project
     │                  │
     │            ┌─────┴─────┐
     └────────────│  Document │
                  │           │
                  └───────────┘
```

### Tables

- **users** — System users (id, username, password, fullName, role, createdAt)
- **projects** — Research projects (id, title, summary, status, pi_id, tags, dates, timestamps)
- **milestones** — Project milestones (id, project_id, title, description, dueDate, isCompleted, created_by)
- **documents** — Research documents (id, project_id, title, description, urlOrPath, uploaded_by, uploadedAt)

---

## 📊 Sample Data

The `src/main/resources/sample-data.sql` file contains test data:

- **6 Users** (1 Admin, 2 PIs, 2 Members, 1 Viewer)
- **3 Projects** (AI, Blockchain, IoT research)
- **5 Milestones** across projects
- **4 Documents** linked to projects

> **Default password for all sample users:** `password123`

---

## 🧪 Testing with Postman

### 1. Register a New User

```
POST http://localhost:8081/api/auth/signup
Content-Type: application/json

{
    "username": "testuser@research.edu",
    "password": "password123",
    "fullName": "Test User"
}
```

### 2. Login

```
POST http://localhost:8081/api/auth/login
Content-Type: application/json

{
    "username": "testuser@research.edu",
    "password": "password123"
}
```

### 3. Create a Project (use Bearer token)

```
POST http://localhost:8081/api/projects
Authorization: Bearer <token>
Content-Type: application/json

{
    "title": "New Research Project",
    "summary": "A project exploring new frontiers.",
    "status": "PLANNING",
    "piId": "<user-id>",
    "tags": "Research, Innovation",
    "startDate": "2025-07-01",
    "endDate": "2026-07-01"
}
```

### 4. Add a Milestone

```
POST http://localhost:8081/api/projects/<project-id>/milestones
Authorization: Bearer <token>
Content-Type: application/json

{
    "title": "Phase 1 - Research",
    "description": "Complete initial research phase",
    "dueDate": "2025-09-30",
    "isCompleted": false
}
```

### 5. Upload a Document

```
POST http://localhost:8081/api/projects/<project-id>/documents
Authorization: Bearer <token>
Content-Type: application/json

{
    "title": "Research Proposal",
    "description": "Initial proposal document",
    "urlOrPath": "/documents/proposal.pdf"
}
```

---

## 📦 Response Format

All API responses follow this consistent JSON structure:

```json
{
    "success": true,
    "message": "Operation completed successfully",
    "data": { }
}
```

Error responses:

```json
{
    "success": false,
    "message": "Error description",
    "data": null
}
```

Validation errors:

```json
{
    "success": false,
    "message": "Validation failed",
    "data": {
        "fieldName": "Error message"
    }
}
```

---

## 👤 Author

IJSE CMJD Coursework - Research Project Tracker System

---

## 📄 License

This project is developed for educational purposes as part of the CMJD programme.
