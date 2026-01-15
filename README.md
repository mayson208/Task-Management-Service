# Task Management Service

A cleanly architected, multi-module Java backend service built with **Spring Boot** and **Gradle**.  
This project demonstrates professional backend design principles including hexagonal architecture,
domain-driven design, integration testing, and CI automation.

---

## Tech Stack

- Java 21
- Gradle (multi-module)
- Spring Boot
- Spring Web / JPA
- H2 (local runtime)
- OpenAPI / Swagger
- GitHub Actions CI

---

## Architecture Overview

This project follows a **hexagonal (ports and adapters) architecture**.


### Key Principles

- The **domain layer** has no framework dependencies
- Spring is used only at the application boundary
- Dependencies point inward
- Business rules are enforced in the domain
- Infrastructure details are replaceable

---

## Modules

| Module | Responsibility |
|------|----------------|
| `core-domain` | Domain entities, value objects, domain rules |
| `application` | Use cases and service orchestration |
| `persistence` | JPA entities and repository implementations |
| `api` | REST API, DTOs, validation, Swagger |

---

## API Documentation

Swagger UI is available at:


---

## Running the Application

From the project root:

```bash
./gradlew :api:bootRun
