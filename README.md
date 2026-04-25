# 📌 CRM Backend Service (Lead Management):

---

## 🚀 Project Overview

This project is an **industry-style CRM backend system** built using **Spring Boot** with layered architecture.

Current implementation:

* Lead Management Module
* Full CRUD APIs
* Validation
* Exception Handling
* Swagger API Documentation
* MySQL Integration
* DTO + Mapper Pattern
* Standard API Response Structure

---

## 🏗️ Architecture

Project follows **Layered Architecture**:

```
Controller → Service → Repository → Database
```

### Why this approach?

* Clean separation of concerns
* Easy maintenance
* Scalable design
* Testable code

---

## 📂 Package Structure

```
com.crm
│
├── controller       → REST APIs
├── service          → Interfaces
├── serviceimpl      → Business logic
├── repository       → Database layer (JPA)
├── entity           → Database models
├── dto              → Request/Response objects
├── mapper           → DTO ↔ Entity conversion
├── exception        → Custom & global exceptions
├── config           → Swagger & configs
├── enums            → Status enums
```

---

## ⚙️ Tech Stack

* Java 17
* Spring Boot
* Spring Data JPA
* MySQL
* Lombok
* Swagger (OpenAPI)

---

## 🔌 Setup Instructions

### 1. Clone Project

```bash
git clone <repo-url>
cd crm-backend-service
```

---

### 2. Configure Database

Update `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/crm_db
    username: root
    password: root

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

---

### 3. Run Application

```bash
mvn spring-boot:run
```

---

### 4. Open Swagger

```
http://localhost:8080/swagger-ui/index.html
```

---

## 📌 Lead Module

---

### 🔹 Entity Fields

* id
* fullName
* email
* phone
* source
* status (ENUM)
* assignedTo

---

### 🔹 Enum (LeadStatus)

```
NEW
CONTACTED
QUALIFIED
LOST
CONVERTED
```

---

## 📥 APIs

---

### ✅ 1. Create Lead

**POST** `/api/leads`

```json
{
  "fullName": "Asif",
  "email": "asif@gmail.com",
  "phone": "9876543210",
  "source": "Website",
  "assignedTo": "Sales Team A"
}
```

---

### ✅ 2. Get All Leads

**GET** `/api/leads`

---

### ✅ 3. Get Lead By ID

**GET** `/api/leads/{id}`

---

### ✅ 4. Update Lead

**PUT** `/api/leads/{id}`

---

### ✅ 5. Delete Lead

**DELETE** `/api/leads/{id}`

---

### ✅ 6. Search by Status

**GET** `/api/leads/status/{status}`

Example:

```
/api/leads/status/NEW
```

---

## 🔄 API Response Format

```json
{
  "success": true,
  "message": "Lead created successfully",
  "data": {
    "id": 1,
    "fullName": "Asif",
    "email": "asif@gmail.com"
  }
}
```

---

## ❌ Error Response Format

```json
{
  "timestamp": "2026-04-25T20:00:00",
  "message": "Lead not found",
  "status": 404
}
```

---

## 🔐 Validation

Used annotations:

* `@NotBlank`
* `@Email`
* `@Valid`

---

## ⚠️ Exception Handling

### Custom Exception:

* `ResourceNotFoundException`

### Global Handler:

* `@RestControllerAdvice`

---

## 🔁 DTO & Mapper

### Why DTO?

* Security
* Decoupling from DB
* Flexible API responses

### Mapper Role:

* Convert Request DTO → Entity
* Convert Entity → Response DTO

---

## 🧪 Testing

### Swagger UI

Use browser to test APIs.

---

### Test Flow

1. Create Lead
2. Get All Leads
3. Get By ID
4. Update
5. Search
6. Delete

---

### Negative Testing

* Invalid email
* Non-existing ID
* Duplicate email

---

## 📊 Database Check

```sql
SELECT * FROM leads;
```

---

## 🧠 Key Concepts Covered

* Layered Architecture
* DTO Pattern
* Exception Handling
* Validation
* JPA Repository
* Enum usage
* API Design

---

## 💬 Important Interview Points

---

### Q1. Why DTO instead of Entity?

DTO provides abstraction and avoids exposing database structure.

---

### Q2. Why Service Layer?

To separate business logic from controller.

---

### Q3. Why Global Exception Handler?

Centralized error handling.

---

### Q4. Why Enum for status?

* Avoid typo issues
* Type safety
* Controlled values

---

### Q5. Why JpaRepository?

Provides ready CRUD operations and reduces boilerplate code.

---

## 🔁 Alternative Approaches

| Feature      | Alternative             |
| ------------ | ----------------------- |
| Mapper       | MapStruct / ModelMapper |
| DB Migration | Flyway / Liquibase      |
| Architecture | Clean / Hexagonal       |
| Query        | @Query / Criteria API   |

---

## 📝 Git Commits (Day-wise)

### Day 1

```
Initial project setup with layered architecture, MySQL, and Swagger configuration
Added Lead entity, DTOs, enums, and initial CRM module structure
```

---

### Day 2

```
Added LeadRepository using Spring Data JPA
Added LeadService interface
Added LeadMapper
Implemented LeadServiceImpl
Added LeadController APIs
```

---

### Day 3

```
Added update lead API
Added delete lead API
Added custom ResourceNotFoundException
Implemented global exception handler
Added duplicate email validation
Added search by status API
Added standardized API response wrapper
```

---

## ✅ Milestone Status

✔ Lead CRUD complete
✔ Validation complete
✔ Exception handling complete
✔ Swagger integrated
✔ API structure production-ready

---

## 🚀 Next Steps (Day 4)

* Ticket Module
* Audit Logs
* Entity Relationships (OneToMany)
* Advanced JPA

---

## 👨‍💻 Author

Asif Kalim
(Java Backend Developer – Spring Boot)

---

🔥 **Note:** This project structure is aligned with real-world industry backend systems and is highly useful for interviews.

---
