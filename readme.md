# Payment Service API

A small Kotlin/Spring Boot backend that models merchant creation, payment initiation, transaction listing, and merchant settlement batching.

## Requirements Covered

- Create merchants with business name, email, settlement account, and status.
- Initiate transactions with amount, currency, merchant reference, merchant ID, generated internal reference, and a simulated successful customer debit.
- Calculate a flat fee of 1.5 percent capped at 200.00.
- List transactions by merchant, with optional status and date filters.
- Settle unsettled successful transactions into a settlement batch.
- Mark transactions as settled after batching so the same transaction is not settled twice.
- Use a relational database with explicit SQL through `JdbcTemplate`.
- Document REST endpoints with a local OpenAPI file.

## Tech Stack

- Kotlin
- Spring Boot Web
- Spring JDBC
- MySQL
- Gradle

## Architecture

The service is split into controller, service, repository, DTO, mapper, and model packages.

- Controllers expose REST endpoints and convert domain models to API responses.
- Services own business rules such as fee calculation, idempotency, and settlement eligibility.
- Repositories own SQL and persistence details using `JdbcTemplate`.
- Models are plain Kotlin domain objects, not ORM entities.
- DTOs keep API contracts separate from internal models.

The repository layer uses raw SQL instead of a heavy ORM so table structure, joins, and update behavior remain explicit.

## Setup

1. Create a MySQL database:

   ```sql
   CREATE DATABASE payment_service_db;
   ```

2. Configure database credentials in `src/main/resources/application.properties`:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/payment_service_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.sql.init.mode=always
   ```

3. Run the application:

   ```bash
   ./gradlew bootRun
   ```

The API runs at `http://localhost:8080`.

## API Documentation

OpenAPI documentation is available at `docs/openapi.yaml`.

Main endpoints:

- `POST /api/merchants`
- `POST /api/transactions`
- `GET /api/transactions?merchantId={merchantId}&status=SUCCESS&from=2026-01-01T00:00:00&to=2026-01-31T23:59:59`
- `POST /api/merchants/{merchantId}/settlements`

Legacy aliases are still supported:

- `POST /api/transactions/initiate`
- `GET /api/transactions/view`
- `POST /api/settlements/settle?merchantId={merchantId}`

## Assumptions

- A valid initiated transaction simulates a successful customer debit and is saved with `SUCCESS` status.
- `merchantRef` is idempotent per merchant. Reusing the same merchant reference returns the existing transaction.
- Settlement `totalAmount` is the net amount payable to the merchant after fees.
- Only `SUCCESS` transactions with `settled = false` are eligible for settlement.
- Authentication is not implemented in this version.

## Tests

Run tests with:

```bash
./gradlew test
```

Note: the Gradle wrapper jar must be present at `gradle/wrapper/gradle-wrapper.jar` for wrapper commands to work.
