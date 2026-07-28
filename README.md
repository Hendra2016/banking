# Banking Loan Origination Platform

This repository contains a Spring Boot microservices system for loan origination with event-driven communication over Kafka, PostgreSQL persistence, API gateway routing, and observability tooling.

## Project Structure

```text
banking/
|- customer-service/       # Customer profile API (REST + JPA)
|- loan-service/           # Loan application API + workflow orchestration
|- slik-service/           # SLIK integration + resilience + audit log
|- risk-service/           # Risk scoring consumer/producer
|- notification-service/   # Notification consumer (scoring results)
|- gateway/                # Spring Cloud Gateway entry point
|- db/                     # Postgres init script
|- prometheus/             # Prometheus scrape config
|- otel/                   # OpenTelemetry collector config
|- loki/                   # Promtail config for logs
`- docker-compose.yml      # Full stack orchestration
```

## Technology Stack (Detected from Code)

- Java microservices with Spring Boot (`4.1.0` parent in all `pom.xml` files)
- REST APIs:
  - `spring-boot-starter-webmvc` (`customer-service`, `loan-service`)
  - `spring-boot-starter-webflux` (`slik-service`, `risk-service`, `notification-service`, `gateway`)
- Messaging: Apache Kafka (`spring-kafka`)
- Persistence: PostgreSQL + Spring Data JPA
- Validation: Jakarta Validation (`spring-boot-starter-validation`)
- Resilience: Resilience4j (`slik-service`, `risk-service`)
- API docs: SpringDoc OpenAPI (`slik-service`, `risk-service`)
- Observability:
  - Spring Boot Actuator
  - Micrometer Prometheus registry
  - Micrometer tracing bridge + OpenTelemetry OTLP exporter
  - Prometheus + Grafana + Jaeger + Loki + Promtail + OTel Collector (via compose)

## Services and Default Ports

- `gateway-service`: `8080`
- `customer-service`: `8081`
- `loan-service`: `8082`
- `slik-service`: `8083`
- `risk-service`: `8084`
- `notification-service`: `8085`
- Kafka broker: `9092`
- PostgreSQL: `5432`
- Prometheus: `9090`
- Grafana: `3000`
- Jaeger UI: `16686`
- Loki: `3100`

## Prerequisites

### Required

- Docker Desktop (with Compose)
- Git

### Required for local non-Docker runs

- JDK 17 (for `customer-service`, `loan-service`, `slik-service`, `risk-service`, `notification-service`)
- JDK 21 (for `gateway`)
- Maven 3.9+

## Install and Run

## 1) Recommended: Run everything with Docker Compose

From repository root:

```powershell
docker compose up --build -d
```

To check running containers:

```powershell
docker compose ps
```

To stop all services:

```powershell
docker compose down
```

To stop and remove volumes/network:

```powershell
docker compose down -v
```

## 2) Run services locally (advanced)

Use this only if you want to run Java processes directly.

1. Start infrastructure first (`postgres`, `kafka`, observability stack if needed):

```powershell
docker compose up -d postgres kafka otel-collector jaeger prometheus grafana loki promtail
```

2. Install Java and Maven.
3. Run each service in its own terminal from its service directory.

Example (`customer-service`):

```powershell
cd customer-service
mvn spring-boot:run
```

> Note: Current `application.yaml` files are mostly container-oriented (`postgres`, `kafka`, `customer-service` hostnames). For local Java runs, you usually need to override host settings to `localhost` using env vars (for example `SPRING_DATASOURCE_URL`, `SPRING_KAFKA_BOOTSTRAP_SERVERS`, and service URL properties).

## Useful Endpoints

### Through Gateway (`http://localhost:8080`)

- `POST /customers`
- `GET /customers/{id}`
- `POST /applications`
- `GET /applications/{id}`
- `POST /applications/{id}/submit`
- `POST /slik/check`

### Health and Metrics (service direct)

- `http://localhost:8081/actuator/health`
- `http://localhost:8082/actuator/health`
- `http://localhost:8083/actuator/health`
- `http://localhost:8084/actuator/health`
- `http://localhost:8085/actuator/health`

Prometheus scrape endpoint pattern:

- `http://localhost:<service-port>/actuator/prometheus`

## Event-Driven Flow (High Level)

1. Loan submitted -> topic `loan-submitted`
2. SLIK consumes and publishes -> `slik-completed`
3. Risk consumes and publishes -> `scoring-completed`
4. Loan + Notification consume scoring result

## Quick Troubleshooting

- If `mvn` is not found: install Maven and reopen terminal.
- If Java version is wrong: set `JAVA_HOME` and `PATH` to JDK 17/21.
- If a service cannot connect to DB/Kafka during local run: check hostnames in `application.yaml` and override with local values.
- If gateway route fails: verify backend containers are healthy via `docker compose ps`.

## Notes from Current Workspace Scan

- `docker-compose.yml` already includes app services + observability stack.
- `db/init.sql` creates `customerdb`, `loandb`, `riskdb`, `slikdb`.
- Current shell check showed `docker` available, `mvn` not detected.
- Current shell `java` points to legacy Java 8 path; upgrade/switch is required for this project.

