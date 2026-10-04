# Student Registry API

A small Spring Boot REST API to add, list, update and delete students.
Students are stored in **PostgreSQL** through **Hibernate**, and each new student
publishes a `STUDENT_CREATED` event to **Kafka**.

**Full explanation of the design and request flow:** [docs/TECHNICAL_GUIDE.md](docs/TECHNICAL_GUIDE.md)
**Start / stop commands for every component (PDF):** [docs/Start_Stop_Commands.pdf](docs/Start_Stop_Commands.pdf)

## Tech stack

Java 17 · Spring Boot 3.3 · Hibernate (Spring Data JPA) · PostgreSQL · Apache Kafka 3.7 (KRaft) · Docker

## Quick start (Stage 1: local)

Requirements: JDK 17+, Maven, local PostgreSQL with user `student` / `student123` and
database `studentdb`, and Docker Desktop (Kafka only).

```powershell
docker compose up -d     # 1. start Kafka
mvn spring-boot:run      # 2. start the app on http://localhost:8080
```

Try it:

```powershell
$body = @{ name = "Asha Rao"; email = "asha@example.com"; course = "Computer Science" } | ConvertTo-Json
Invoke-RestMethod -Method Post -Uri http://localhost:8080/api/students -ContentType "application/json" -Body $body
Invoke-RestMethod http://localhost:8080/api/students
```

## Endpoints

| Method | URL                  | Result                         |
|--------|----------------------|--------------------------------|
| POST   | /api/students        | 201, creates + publishes event |
| GET    | /api/students        | 200, list                      |
| GET    | /api/students/{id}   | 200, or 404                    |
| PUT    | /api/students/{id}   | 200, or 404 / 409              |
| DELETE | /api/students/{id}   | 204, or 404                    |

## Roadmap

- [x] Stage 1: run locally (local PostgreSQL + Kafka in Docker)
- [ ] Stage 2: full Docker Compose (app + PostgreSQL + Kafka)
- [ ] Stage 3: deploy to AWS EC2
