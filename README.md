# Customer Rewards API

A Spring Boot REST API that calculates customer reward points for each month and the full period. The implementation uses persisted purchase records, so the reported months are discovered from transaction dates and are not hard coded.

## Rules

- Purchases up to and including $50 earn 0 points.
- Each dollar between $50 and $100 earns 1 point.
- Each dollar over $100 earns 2 points.
- Decimal cents are ignored when converting each tier's points to whole points.

For example, a $120 purchase earns `(100 - 50) + (120 - 100) * 2 = 90` points.

## Run

Prerequisites: Java 17 and Maven 3.9+.

```text
mvn spring-boot:run
```

The API is available at `http://localhost:8080`.

Interactive Swagger UI is available at `http://localhost:8080/swagger-ui.html`; the OpenAPI document is available at `http://localhost:8080/v3/api-docs`.

## Endpoint

```text
GET /api/rewards/{customerId}
```

Example response:

```json
{
  "customerId": 1,
  "customerName": "Alice Johnson",
  "monthlyRewards": [
    { "month": "2026-01", "points": 115 },
    { "month": "2026-02", "points": 250 },
    { "month": "2026-03", "points": 0 }
  ],
  "totalPoints": 365
}
```

The sample data is loaded from `src/main/resources/data.sql` into an in-memory H2 database. It includes multiple customers and transactions across three months.

## Structure

- The base package is `com.charter.rewards`, using reverse-domain naming and lowercase package names.
- Packages are organized by responsibility using a conventional layered architecture.
- `entity`: JPA entities such as customers and purchases.
- `repository`: Spring Data repository interfaces.
- `service`: the rewards service contract.
- `service_impl`: the service implementation and business rules.
- `controller`: REST endpoints and Swagger metadata.
- `dto`: API response records.
- `exception`: domain exceptions and REST exception handling.
- `src/test`: unit tests for calculation and aggregation plus MockMvc integration tests for the REST and negative scenarios.

## Verification

```text
mvn test
```

The project excludes Maven output through `.gitignore`; do not commit `target/` or an archive of the project.
