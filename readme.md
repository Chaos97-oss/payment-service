# Payment Service API

A simple payment processing service with merchant management, transaction handling, and settlement processing built with Kotlin and Spring Boot.

---

## Table of Contents

- [Project Overview](#project-overview)
- [Features](#features)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Running Tests](#running-tests)
- [Contributing](#contributing)
- [License](#license)

---

## Project Overview

This service allows creating merchants, initiating transactions, listing transactions, and settling merchant transactions.

Built with Kotlin, Spring Boot, JPA (Hibernate), and uses PostgreSQL/MySQL for persistence.


🧰 Architecture & Decisions
Tech stack: Spring Boot + Kotlin for concise, modern JVM development.

Persistence: JPA/Hibernate to map Kotlin data classes to relational tables.

Domain modeling: Explicit Merchant, Transaction, and SettlementBatch entities to reflect real-world concepts.

DTOs & Mappers: To decouple API contracts from internal entities.

Postman docs: Hosted and linked for easy consumption by frontend or QA.

Why Kotlin?

Null safety and data classes reduce boilerplate.

Interoperable with Java if needed.

Why Postman docs?

Faster onboarding for collaborators and testers.


📝 Assumptions Made
Merchants are created before transactions can be initiated.

Only transactions with TransactionStatus.SUCCESS are eligible for settlement.

Settlements are created per merchant, aggregating all successful transactions at the time of settlement.

No authentication/authorization implemented yet (future improvement).


## 🧪 API Documentation

All API endpoints are documented in the included Postman collection.

### 📄 Postman Collection

## 📄 API Documentation

You can view and test the full API documentation via Postman here:  
👉 [View Postman Documentation](https://documenter.getpostman.com/view/43208616/2sB34eJ2fZ)

Alternatively, you can download the Postman collection JSON file directly from this repository:  
👉 [Download Postman Collection (JSON)](https://github.com/chaos97-oss/payment-service/blob/main/postman/payment-service.postman_collection.json)

Or get the raw JSON:  
👉 [Raw JSON](https://github.com/chaos97-oss/payment-service/raw/main/postman/payment-service.postman_collection.json)


#### 🔗 Import into Postman:
1. Open Postman.
2. Click `Import`.
3. Select the file: `payment-service.postman_collection.json`.
4. Use the `Payment Service API` collection in your workspace.

---

### 🛣 Example Endpoints

✅ Create Merchant  
`POST /api/merchants`

✅ Get Merchants  
`GET /api/merchants`

✅ Initiate Transaction  
`POST /api/transactions/initiate`

✅ List Transactions  
`GET /api/transactions`

✅ Settle Transactions  
`POST /api/settlements/settle?merchantId=<merchant-id>`

---

## Features

- Create and manage merchants
- Initiate and list transactions with filtering options
- Settle transactions for merchants in batches
- RESTful API design

---

## Getting Started

### Prerequisites

- Java 17+
- Gradle 7+
- PostgreSQL or MySQL (or your preferred RDBMS)
- Postman (optional, for testing APIs)

### Setup

1. Clone the repo:

   ```bash
   git clone https://github.com/chaoss97-oss/payment-service.git
   cd payment-service

2. Configure your database connection in src/main/resources/application.properties or application.yml:



### Properties
  spring.datasource.url=jdbc:postgresql://localhost:5432/payment_db
  pring.datasource.username=your_username
  spring.datasource.password=your_password  
  spring.jpa.hibernate.ddl-auto=update
  
###


3. 
  
  ### Build and run the app:
  /gradlew bootRun
  ###
  The API will be available at: http://localhost:8080

4.
API Documentation
You can explore and test the API endpoints using our Postman Collection.
###
https://documenter.getpostman.com/view/43208616/2sB34eJ2fZ
###


Feel free to open issues or submit pull requests!!!