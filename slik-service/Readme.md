# Loan Origination System - Microservices Architecture

## Overview

This project implements an event-driven loan origination platform using Spring Boot, Kafka, PostgreSQL, WebClient, and Resilience4j.

Services:

```text
customer-service
loan-service
slik-service
risk-service
```

---

# Architecture

```text
+----------------+
| Customer       |
+-------+--------+
        |
        v
+----------------+
| Loan Service   |
+-------+--------+
        |
        | LOAN_SUBMITTED
        v
+----------------+
| Kafka          |
+-------+--------+
        |
        v
+----------------+
| SLIK Service   |
+-------+--------+
        |
        | SLIK_COMPLETED
        v
+----------------+
| Kafka          |
+-------+--------+
        |
        v
+----------------+
| Risk Service   |
+-------+--------+
        |
        | SCORING_COMPLETED
        v
+----------------+
| Kafka          |
+-------+--------+
        |
        v
+----------------+
| Loan Service   |
+----------------+
```

---

# Loan Service

## Responsibilities

- Create Loan Application
- Submit Loan Application
- Track Application Status
- Publish Loan Events
- Consume Scoring Events

## APIs

### Create Application

```http
POST /applications
```

Request:

```json
{
  "customerId": "CUS001",
  "amount": 100000000,
  "tenure": 36
}
```

### Get Application

```http
GET /applications/{id}
```

### Submit Application

```http
POST /applications/{id}/submit
```

---

## Entity

```java
Loan
```

Fields:

```java
applicationId
customerId
nik
customerName
amount
tenure
status
```

---

## Status Flow

```text
DRAFT
 ↓
SUBMITTED
 ↓
SLIK_CHECKING
 ↓
SCORING
 ↓
APPROVED
or
REJECTED
```

---

## Kafka Events

### Produced

Topic:

```text
loan-submitted
```

Payload:

```json
{
  "applicationId":"APP001",
  "customerId":"CUS001",
  "nik":"123456789",
  "customerName":"John Doe",
  "event":"LOAN_SUBMITTED"
}
```

---

### Consumed

#### slik-checking-started

```text
Status -> SLIK_CHECKING
```

#### slik-completed

```text
Status -> SCORING
```

#### scoring-completed

```text
Status -> APPROVED / REJECTED
```

---

# SLIK Service

## Responsibilities

- Call External SLIK
- Retry Failed Calls
- Circuit Breaker
- Time Limiter
- Audit Logging
- Event Publishing

---

## API

### Check SLIK

```http
POST /slik/check
```

Request:

```json
{
  "nik":"123456789",
  "name":"John Doe"
}
```

Response:

```json
{
  "result":"FOUND",
  "collectibility":2,
  "activeLoans":3
}
```

---

## WebClient

```java
@Bean
public WebClient slikWebClient(
        WebClient.Builder builder) {

    return builder
            .baseUrl(slikUrl)
            .defaultHeader(
                    HttpHeaders.CONTENT_TYPE,
                    MediaType.APPLICATION_JSON_VALUE
            )
            .build();
}
```

---

## Resilience4j

```java
@TimeLimiter(name="slik")
@Retry(name="slik")
@CircuitBreaker(
        name="slik",
        fallbackMethod="fallback")
```

---

## Configuration

```yaml
resilience4j:

  retry:
    instances:
      slik:
        max-attempts: 3
        wait-duration: 2s

  circuitbreaker:
    instances:
      slik:
        sliding-window-size: 10
        minimum-number-of-calls: 5
        failure-rate-threshold: 50

  timelimiter:
    instances:
      slik:
        timeout-duration: 5s
```

---

## Audit Log

Table:

```text
audit_log
```

Fields:

```text
id
requestId
applicationId
serviceName
requestPayload
responsePayload
status
createdDate
```

---

## Events

### Consumes

Topic:

```text
loan-submitted
```

Event:

```java
LoanSubmittedEvent
```

---

### Produces

Topic:

```text
slik-completed
```

Event:

```java
SlikCompletedEvent
```

Payload:

```json
{
  "applicationId":"APP001",
  "customerId":"CUS001",
  "result":"FOUND",
  "collectibility":2,
  "activeLoans":3,
  "event":"SLIK_COMPLETED"
}
```

---

# Risk Service

## Responsibilities

- Consume SLIK Result
- Calculate Risk Score
- Persist Score
- Publish Scoring Result

---

## Entity

```java
RiskScore
```

Fields:

```java
scoreId
applicationId
score
decision
```

---

## Scoring Logic

Initial Score:

```text
100
```

Rules:

```java
if (collectibility >= 2)
    score -= 40;

if (activeLoans > 3)
    score -= 20;
```

Decision:

```java
score >= 70
    APPROVED
else
    REJECTED
```

---

## Events

### Consumes

Topic:

```text
slik-completed
```

Event:

```java
SlikCompletedEvent
```

---

### Produces

Topic:

```text
scoring-completed
```

Event:

```java
ScoringCompletedEvent
```

Payload:

```json
{
  "applicationId":"APP001",
  "score":60,
  "decision":"REJECTED",
  "event":"SCORING_COMPLETED"
}
```

---

# Customer Service

## Responsibilities

- Customer Profile Management
- Customer Validation
- Customer Information Retrieval

Future APIs:

```http
POST /customers
GET /customers/{id}
PUT /customers/{id}
```

---

# Kafka Topics

```text
loan-submitted
slik-checking-started
slik-completed
scoring-completed
```

Optional:

```text
loan-submitted-dlt
slik-completed-dlt
scoring-completed-dlt
```

---

# Databases

Each service owns its database.

```text
customer-db
loan-db
slik-db
risk-db
audit-db
```

Avoid:

```text
shared-db
```

---

# Security

## External Access

```text
OAuth2
JWT
Rate Limiting
IP Whitelisting
```

---

## Service-to-Service

```text
JWT Propagation
mTLS
```

---

## Secrets Management

Never hardcode:

```yaml
username=admin
password=admin
```

Use:

```text
Vault
Azure Key Vault
AWS Secrets Manager
```

---

# Monitoring

Dependencies:

```text
Spring Boot Actuator
```

Endpoints:

```http
/actuator/health
/actuator/info
/actuator/metrics
```

---

# Observability

Recommended:

```text
Correlation ID
Centralized Logging
Prometheus
Grafana
```

Header:

```text
X-Request-Id
```

---

# Current Project Status

## Completed

- Loan Service
- Kafka Producer
- Kafka Consumers
- Application Workflow
- SLIK Service
- WebClient Integration
- Retry
- Circuit Breaker
- Time Limiter
- Audit Logging
- Risk Service
- Event Driven Flow

## Planned

- Notification Service
- API Gateway
- JWT Authentication
- Liquibase / Flyway
- Docker Compose
- Prometheus
- Grafana
- CI/CD Pipeline

---

# End-to-End Flow

```text
Create Application
        ↓
DRAFT
        ↓
Submit
        ↓
SUBMITTED
        ↓
LOAN_SUBMITTED
        ↓
Kafka
        ↓
SLIK Service
        ↓
SLIK_CHECKING
        ↓
SLIK_COMPLETED
        ↓
Kafka
        ↓
Risk Service
        ↓
SCORING
        ↓
SCORING_COMPLETED
        ↓
Kafka
        ↓
Loan Service
        ↓
APPROVED / REJECTED
```
