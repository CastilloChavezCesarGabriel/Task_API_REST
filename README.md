# Task API REST - Spring Boot

This REST API orchestrates tasks between Android apps by exposing a task management domain through
HTTP endpoints, where clients create, complete and remove tasks that transition through PENDING,
IN_PROGRESS and COMPLETED statuses. Each incoming request flows through a strictly layered Clean
Architecture — from the controller down to the use case and into the repository — and returns a typed
result object that drives the HTTP response without coupling the domain to the web layer. No domain
object ever exposes its internal state directly: all data access is handled exclusively through the
Visitor pattern, where objects push their data to named visitor implementations instead of responding
to getter calls.

## Clean Architecture Structure

- **Domain**: Pure Java classes with zero framework dependencies. `Task`, `TaskIdentity` and `TaskState`
  encapsulate behavior without getters. `ITaskRepository` defines the contract for persistence.
- **Application**: Use cases (`CreateTaskUseCase`, `CompleteTaskUseCase`, `RemoveTaskUseCase`,
  `ListTasksUseCase`) each do one thing and return typed result objects. `TaskOperations` acts as a
  facade, giving the controller a single entry point.
- **Infrastructure**: Spring Boot wiring. `TaskController` handles HTTP. `InMemoryTaskRepository`
  stores tasks in memory. Response builders translate result objects into `ResponseEntity` responses.

## Java Libraries Used

- **org.springframework.boot:spring-boot-starter-web**: Provides the embedded server, REST controller
  support and JSON serialization via Jackson.
- **org.springframework.boot:spring-boot-starter-test**: Provides JUnit 5 and MockMvc for unit and
  integration testing.
- **java.util.UUID**: Generates unique identifiers for tasks at creation time.
- **java.util.concurrent.ConcurrentHashMap**: Thread-safe in-memory storage for the task repository.
- **java.util.LinkedHashMap**: Preserves field insertion order in JSON responses.

## Dependencies

- Java 21+
- Maven (via wrapper — no global installation required)

## Features

- Create tasks with a title and description
- List all tasks or filter by status (`PENDING`, `IN_PROGRESS`, `COMPLETED`)
- Complete a task by identifier, transitioning its status
- Remove a task by identifier
- Input validation with descriptive error responses
- No getters on domain objects — data access exclusively through the Visitor pattern
- Strict 2-parameter maximum across all methods, constructors and lambdas
- Result objects with `provide()` method for decoupled response building
- 16 automated tests covering domain, use cases and HTTP endpoints

## API Reference

| Method   | URL                            | Action           | Success | Failure |
|----------|--------------------------------|------------------|---------|---------|
| `POST`   | `/tasks`                       | Create a task    | 201     | 400     |
| `GET`    | `/tasks`                       | List all tasks   | 200     | —       |
| `GET`    | `/tasks?status=PENDING`        | Filter by status | 200     | —       |
| `PUT`    | `/tasks/{identifier}/complete` | Complete a task  | 200     | 404     |
| `DELETE` | `/tasks/{identifier}`          | Remove a task    | 200     | 404     |

## Architecture

```
TaskAPI/
├── src/main/java/com/taskapi/
│   ├── domain/
│   │   ├── Task.java                        # Domain entity, behavior-driven, no getters
│   │   ├── TaskIdentity.java                # Value object grouping identifier and title
│   │   ├── TaskState.java                   # Value object grouping description and status
│   │   ├── TaskStatus.java                  # Enum: PENDING, IN_PROGRESS, COMPLETED
│   │   ├── ITaskVisitor.java                # Visitor interface for Task data access
│   │   ├── ITaskIdentityVisitor.java        # Visitor interface for TaskIdentity data access
│   │   ├── ITaskStateVisitor.java           # Visitor interface for TaskState data access
│   │   └── ITaskRepository.java            # Repository contract
│   ├── application/
│   │   ├── TaskOperations.java             # Facade grouping all use cases
│   │   ├── result/
│   │   │   ├── CreateTaskResult.java        # Result object with static factories and provide()
│   │   │   ├── ICreateResultConsumer.java   # Consumer interface for create results
│   │   │   ├── CompleteTaskResult.java      # Result object for complete operation
│   │   │   ├── ICompleteResultConsumer.java # Consumer interface for complete results
│   │   │   ├── RemoveTaskResult.java        # Result object for remove operation
│   │   │   └── IRemoveResultConsumer.java   # Consumer interface for remove results
│   │   └── usecase/
│   │       ├── CreateTaskUseCase.java       # Validates and stores a new task
│   │       ├── CompleteTaskUseCase.java     # Transitions task status to COMPLETED
│   │       ├── RemoveTaskUseCase.java       # Removes a task by identifier
│   │       └── ListTasksUseCase.java        # Returns all or filtered tasks
│   └── infrastructure/
│       ├── TaskApiApplication.java          # Spring Boot entry point
│       ├── persistence/
│       │   ├── InMemoryTaskRepository.java  # ConcurrentHashMap-based implementation
│       │   └── IdentifierExtractor.java     # Visitor that extracts identifier for map key
│       └── web/
│           ├── TaskController.java          # REST controller with one param: TaskOperations
│           ├── request/
│           │   └── CreateTaskRequest.java   # DTO for POST /tasks request body
│           └── response/
│               ├── TaskResponseWriter.java      # Visitor that builds JSON map from Task data
│               ├── CreateResponseBuilder.java   # Builds HTTP 201 or 400 responses
│               ├── CompleteResponseBuilder.java # Builds HTTP 200 or 404 responses
│               └── RemoveResponseBuilder.java   # Builds HTTP 200 or 404 responses
└── src/test/java/com/taskapi/
    ├── domain/
    │   └── TaskTest.java                    # Domain behavior and Visitor correctness
    ├── application/usecase/
    │   └── CreateTaskUseCaseTest.java       # Use case validation and result routing
    └── infrastructure/web/
        └── TaskControllerTest.java          # Full HTTP integration tests via MockMvc
```

## Design Patterns

| Pattern            | Usage                                                                                                   |
|--------------------|---------------------------------------------------------------------------------------------------------|
| **Visitor**        | `Task`, `TaskIdentity` and `TaskState` push their data to visitors — no getters anywhere in the domain |
| **Result Object**  | Each use case returns a typed result with `provide()`, decoupling use case logic from HTTP concerns     |
| **Facade**         | `TaskOperations` groups all four use cases behind a single injectable dependency                         |
| **Factory Method** | `CreateTaskResult.accept()` / `reject()` — static factories communicate outcome without exposing fields |

## Program Flow

1. When the application starts, Spring Boot scans the packages and wires everything together automatically.
   `InMemoryTaskRepository` is registered as a `@Repository`, `TaskOperations` as a `@Service`, and
   `TaskController` as a `@RestController`. Spring injects `InMemoryTaskRepository` into `TaskOperations`,
   and `TaskOperations` into `TaskController` — all through constructor injection with a single parameter each.

2. When a client (an Android app, curl, or Postman) sends an HTTP request, `TaskController` receives it
   and reads the request data — either a JSON body for creation, a path variable for completion and removal,
   or a query parameter for status filtering. The controller immediately delegates to `TaskOperations` without
   performing any business logic itself.

3. `TaskOperations` forwards the call to the appropriate use case. The use case validates the input — for
   example, `CreateTaskUseCase` rejects a blank title immediately — and interacts with `ITaskRepository` to
   store, find or remove tasks. It then wraps the outcome in a typed result object such as `CreateTaskResult`,
   using static factory methods `accept()` or `reject()` to communicate success or failure without exposing
   internal state.

4. Back in the controller, a response builder such as `CreateResponseBuilder` is created and passed to
   `result.provide()`. The result object calls either `accept()` or `reject()` on the builder, which
   constructs the appropriate `ResponseEntity` — HTTP 201 for a created task, 400 for a validation failure,
   404 for a task not found, or 200 for a successful operation.

5. When the response needs to include task data, `TaskResponseWriter` is used as a Visitor. The controller
   calls `task.accept(writer)`, and `Task` pushes its `TaskIdentity` and `TaskState` value objects into
   the writer. Each value object in turn calls its own visitor method, which writes the fields into a
   `LinkedHashMap`. Spring Boot serializes that map to JSON automatically — no getters are ever called on
   any domain object throughout the entire flow.

## Setting up on Mac

Before running the project, make sure you have the following installed:

1. **Java 21+**: [Temurin Downloads](https://adoptium.net/)
2. **Maven Wrapper**: Included in the repository — no global Maven installation required.

### Steps

1. Check that Java is installed:
   ```bash
   java -version
   ```
2. Clone the repository and create a new working branch (since the **main** branch is reserved for production code only):
   ```bash
   git clone <repository-url>
   cd TaskAPI
   git checkout -b <branch-name>
   ```
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
4. Run the tests:
   ```bash
   ./mvnw test
   ```
5. Test an endpoint (in a separate terminal while the server is running):
   ```bash
   curl -X POST http://localhost:8080/tasks \
     -H "Content-Type: application/json" \
     -d '{"title": "Learn REST", "description": "Build first API"}'
   ```

## Setting up on Windows

Before running the project, make sure you have the following installed:

1. **Java 21+**: [Temurin Downloads](https://adoptium.net/)
2. **Maven Wrapper**: Included in the repository — no global Maven installation required.

### Steps

1. Check that Java is installed (open Command Prompt or PowerShell):
   ```cmd
   java -version
   ```
   If `java` is not recognized, add Java to your system PATH. The default installation path is:
   ```
   C:\Program Files\Eclipse Adoptium\jdk-21\bin\
   ```
2. Clone the repository and create a new working branch (since the **main** branch is reserved for production code only):
   ```cmd
   git clone <repository-url>
   cd TaskAPI
   git checkout -b <branch-name>
   ```
3. Run the application:
   ```cmd
   mvnw.cmd spring-boot:run
   ```
4. Run the tests:
   ```cmd
   mvnw.cmd test
   ```
5. Test an endpoint (in a separate terminal while the server is running):
   ```cmd
   curl -X POST http://localhost:8080/tasks -H "Content-Type: application/json" -d "{\"title\": \"Learn REST\", \"description\": \"Build first API\"}"
   ```

## License

Creative Commons Attribution 4.0 International License (CC BY 4.0).

## Acknowledgements

This project was created as an educational example for demonstrating Clean Architecture and
behavior-driven design in Java with Spring Boot.
