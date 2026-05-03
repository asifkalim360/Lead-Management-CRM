# 📌 CRM Backend Service (Lead + Ticket Management)

---

## 🚀 Project Overview

This project is an **industry-style CRM backend system** built using **Spring Boot** following a layered architecture.

It covers:

* Lead Management
* Ticket Management
* User Assignment
* Audit Logging (with JSON support)
* Pagination & Sorting
* Advanced JPA Queries

---

## 🏗️ Architecture

Project follows **Layered Architecture**:

```
Controller → Service → Repository → Database
```

### ✅ Benefits

* Clean separation of concerns
* Scalable design
* Easy testing & maintenance

---

## 📂 Package Structure

```
com.crm
│
├── controller       → REST APIs
├── service          → Interfaces
├── serviceimpl      → Business logic
├── repository       → JPA Repositories
├── entity           → Database entities
├── dto              → Request/Response objects
├── mapper           → DTO ↔ Entity conversion
├── exception        → Custom exceptions
├── config           → Configurations (Swagger, ObjectMapper)
├── enums            → Enums (Status, Priority)
```

---

## ⚙️ Tech Stack

* Java 17
* Spring Boot
* Spring Data JPA
* MySQL
* Lombok
* Swagger (OpenAPI)
* Jackson (JSON processing)

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

# 📌 MODULES

---

## 🚀 Day 1–3: Lead Management

---

### Features

* Lead CRUD APIs
* Validation (`@NotBlank`, `@Email`)
* Exception Handling
* DTO + Mapper pattern
* Standard API Response

---

### Lead Fields

* id
* fullName
* email
* phone
* source
* status (ENUM)
* assignedTo

---

### LeadStatus Enum

```
NEW, CONTACTED, QUALIFIED, LOST, CONVERTED
```

---

### APIs

* POST `/api/leads`
* GET `/api/leads`
* GET `/api/leads/{id}`
* PUT `/api/leads/{id}`
* DELETE `/api/leads/{id}`
* GET `/api/leads/status/{status}`

---

---

## 🚀 Day 4: Ticket Module & Relationships

---

### Features

* Ticket CRUD APIs
* Lead → Ticket relationship (OneToMany)
* User Assignment (Assign/Reassign)
* Filtering by Status & Priority

---

### Entity Relationships

```
Lead (1) ---------> (Many) Ticket
User (1) ---------> (Many) Ticket
```

---

### Ticket Fields

* id
* title
* description
* status (ENUM)
* priority (ENUM)
* leadId
* assignedUser

---

### Enums

#### TicketStatus

```
OPEN, IN_PROGRESS, RESOLVED, CLOSED
```

#### TicketPriority

```
LOW, MEDIUM, HIGH
```

---

### APIs

#### 🎟 Ticket APIs

* POST `/api/tickets`
* GET `/api/tickets`
* GET `/api/tickets/{id}`
* PUT `/api/tickets/{id}`
* DELETE `/api/tickets/{id}`

#### 🔍 Filtering

* GET `/api/tickets/status/{status}`
* GET `/api/tickets/priority/{priority}`

#### 👤 User APIs

* POST `/api/users`
* GET `/api/users`

---

---

## 🚀 Day 5: Audit Logging System

---

### Features

* Track CREATE, UPDATE, DELETE operations
* Store old vs new data
* Store timestamp & user
* Centralized logging

---

### AuditLog Fields

* id
* action
* entityName
* entityId
* performedBy
* oldValue
* newValue
* timestamp

---

### Example Data

| Action | Old Value | New Value    |
| ------ | --------- | ------------ |
| CREATE | null      | new ticket   |
| UPDATE | old data  | updated data |
| DELETE | old data  | null         |

---

### SQL Check

```sql
SELECT * FROM audit_logs;
```

---

---

## 🚀 Day 6: Advanced Features

---

### 🔥 1. JSON Audit Logging

* Replaced `toString()` with JSON
* Used Jackson `ObjectMapper`

#### Benefits:

* Structured logs
* Easy debugging
* Industry standard

---

### 🔥 2. Pagination & Sorting

#### API:

```
GET /api/tickets?page=0&size=5&sortBy=id&direction=asc
```

#### Benefits:

* Performance optimization
* Handles large datasets

---

### 🔥 3. Custom JPA Query

#### API:

```
GET /api/tickets/filter?priority=HIGH&status=OPEN
```

#### Implementation:

* `@Query` annotation
* Combined filtering

---

# 🔄 API RESPONSE FORMAT

---

## ✅ Success

```json
{
  "success": true,
  "message": "Operation successful",
  "data": {}
}
```

---

## ❌ Error

```json
{
  "timestamp": "2026-04-25T20:00:00",
  "message": "Resource not found",
  "status": 404
}
```

---

# 🧠 KEY CONCEPTS COVERED

---

* Layered Architecture
* DTO Pattern
* Exception Handling
* JPA & Hibernate
* Entity Relationships
* Enum usage
* Audit Logging
* Pagination & Sorting
* Custom Queries

---

# 🧪 TESTING

---

### Swagger UI

```
http://localhost:8080/swagger-ui/index.html
```

---

### Test Flow

1. Create Lead
2. Create User
3. Create Ticket
4. Assign User
5. Update Ticket
6. Delete Ticket
7. Check Audit Logs

---

# 💬 IMPORTANT INTERVIEW QUESTIONS

---

### Q1. Why DTO?

To avoid exposing database structure and for flexible API design.

---

### Q2. Why Service Layer?

To separate business logic from controller.

---

### Q3. Why Audit Logging?

To track system changes for debugging and monitoring.

---

### Q4. Why Enum?

To ensure type safety and avoid invalid values.

---

### Q5. Why Pagination?

To improve performance and handle large datasets.

---

# 🧾 GIT COMMITS SUMMARY

---

### Day 1–3

```
Implemented Lead CRUD with validation and exception handling
Added DTO, Mapper, and Swagger integration
```

---

### Day 4

```
Added Ticket module with relationships and CRUD APIs
Implemented user assignment and filtering features
```

---

### Day 5

```
Added Audit Logging system for tracking changes
Integrated logging in Ticket operations
```

---

### Day 6

```
Implemented JSON-based audit logging using ObjectMapper
Added pagination and sorting support
Implemented custom JPA queries for filtering
```

---

# ✅ PROJECT STATUS

---

✔ Lead Management
✔ Ticket Management
✔ User Assignment
✔ Audit Logging
✔ JSON Logging
✔ Pagination & Sorting
✔ Advanced Queries

---

# 🚀 NEXT STEPS

---

* JWT Authentication
* Role-based access (Admin/User)
* Secure APIs
* Microservices architecture

---

# 👨‍💻 Author

**Asif Kalim**
Java Backend Developer (Spring Boot)

---

🔥 *This project is designed to match real-world backend systems and is highly valuable for interviews.*

---
