# Quiz Application — Monolith

A backend quiz management system built with Spring Boot, following a monolithic architecture. The application handles question management, quiz creation, and quiz evaluation through a layered REST API backed by a PostgreSQL database.

This is the initial single-deployable version of the project. A refactored microservices version is available [here](https://github.com/Prashanth291/quiz-application-microservice).

---

## Architecture

The application follows a standard **monolithic, layered architecture**. All modules — question management, quiz management, business logic, and data access — are packaged into a single deployable JAR.

```
Client (HTTP)
   │
   ▼
┌──────────────────────────────────────────┐
│              Spring Boot App             │
│                                          │
│  ┌────────────┐      ┌───────────────┐   │
│  │ Controller │ ───▶ │   Service     │   │
│  │   Layer    │      │    Layer      │   │
│  └────────────┘      └───────┬───────┘   │
│                              │           │
│                      ┌───────▼───────┐   │
│                      │  Repository   │   │
│                      │    Layer      │   │
│                      └───────┬───────┘   │
│                              │           │
└──────────────────────────────┼───────────┘
                               │
                       ┌───────▼───────┐
                       │  PostgreSQL   │
                       │  (question_db)│
                       └───────────────┘
```

**Layer responsibilities:**

| Layer        | Role                                                        |
|--------------|-------------------------------------------------------------|
| Controller   | Accepts HTTP requests, delegates to service layer            |
| Service      | Contains business logic (quiz creation, scoring, validation) |
| Repository   | Interfaces with the database via Spring Data JPA             |
| Model        | Defines JPA entities and data transfer objects               |

All layers share a single process, a single database, and a single deployment artifact.

---

## Tech Stack

| Component       | Technology                  |
|-----------------|-----------------------------|
| Language        | Java 21                     |
| Framework       | Spring Boot 4.1             |
| Web Layer       | Spring Web MVC              |
| Persistence     | Spring Data JPA / Hibernate |
| Database        | PostgreSQL                  |
| Build Tool      | Maven                       |
| Code Generation | Lombok                      |

---

## Features

- **Question CRUD** — Create, read, update, and delete questions
- **Category Filtering** — Retrieve questions filtered by category
- **Quiz Creation** — Generate a quiz by selecting random questions from a given category
- **Quiz Retrieval** — Fetch quiz questions with answer options (correct answers excluded from response)
- **Quiz Submission & Scoring** — Submit answers and receive a computed score

---

## REST API Endpoints

### Question Endpoints

| Method   | Endpoint                        | Description                          |
|----------|---------------------------------|--------------------------------------|
| `GET`    | `/questions/allQuestions`        | Get all questions                    |
| `GET`    | `/questions/question/{id}`      | Get a question by ID                 |
| `POST`   | `/questions/add-question`       | Add a new question                   |
| `PUT`    | `/questions/update/{id}`        | Update an existing question          |
| `DELETE` | `/questions/delete/{id}`        | Delete a question by ID              |
| `GET`    | `/questions/category/{category}`| Get all questions in a category      |

### Quiz Endpoints

| Method | Endpoint              | Description                                        |
|--------|-----------------------|----------------------------------------------------|
| `POST` | `/quiz/create`        | Create a quiz (params: `category`, `numQues`, `title`) |
| `GET`  | `/quiz/get-quiz/{id}` | Get quiz questions (answers excluded)              |
| `POST` | `/quiz/submit/{id}`   | Submit responses and get score                     |

---

## Project Structure

```
src/main/java/com/prashanth291/quiz/quiz_application/
├── QuizApplication.java              # Application entry point
├── controller/
│   ├── QuestionController.java       # REST endpoints for questions
│   └── QuizController.java           # REST endpoints for quizzes
├── model/
│   ├── Question.java                 # JPA entity — question table
│   ├── QuestionWrapper.java          # DTO — question without correct answer
│   ├── Quiz.java                     # JPA entity — quiz with question list
│   └── QuizResponse.java            # DTO — user's submitted answer
├── repository/
│   ├── QuestionRepository.java       # JPA repository + custom native query
│   └── QuizRepository.java           # JPA repository for quizzes
└── service/
    ├── QuestionService.java          # Business logic for questions
    └── QuizService.java              # Quiz creation, retrieval, scoring
```

---

## How to Run

### Prerequisites

- Java 21
- Maven 3.9+
- PostgreSQL (running locally or remotely)

### 1. Set up the database

Create a PostgreSQL database:

```sql
CREATE DATABASE question_db;
```

### 2. Configure database credentials

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/question_db
spring.datasource.username=<your_username>
spring.datasource.password=<your_password>
```

Hibernate will auto-create/update tables on startup (`ddl-auto=update`).

### 3. Build and run

```bash
./mvnw spring-boot:run
```

Or on Windows:

```cmd
mvnw.cmd spring-boot:run
```

The application starts on `http://localhost:8080`.

---

## Architectural Evolution: Monolith → Microservices

This repository represents the first version of the quiz application, built as a monolith. It was later decomposed into independently deployable services:

| Concern              | Monolith (this repo)          | Microservices version                        |
|----------------------|-------------------------------|----------------------------------------------|
| Deployment           | Single JAR                    | Separate services per domain                 |
| Database             | Single shared PostgreSQL DB   | Database per service                         |
| Communication        | In-process method calls       | REST calls via OpenFeign                     |
| Service Discovery    | N/A                           | Eureka / Spring Cloud                        |
| Gateway              | N/A                           | API Gateway                                  |

The microservices version is available at:
**[quiz-application-microservice](https://github.com/Prashanth291/quiz-application-microservice)**

---

## Why Start with a Monolith

Building the monolith first was a deliberate decision:

1. **Establishes domain boundaries** — Working within a single codebase makes it easier to identify which components have distinct responsibilities and where the natural service boundaries lie.
2. **Reduces initial complexity** — A monolith avoids the overhead of service discovery, inter-service communication, distributed tracing, and independent deployments during early development.
3. **Provides a working baseline** — Having a functional monolith means the microservices refactor can be validated against known-correct behavior.
4. **Reflects real-world practice** — Most production systems begin as monoliths and are decomposed only when scaling or organizational requirements demand it.

The transition from this repository to the microservices version demonstrates the practical process of extracting services from an existing monolith.
