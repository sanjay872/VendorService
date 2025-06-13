### ✅ VendorService – `README.md`

# GadgetHive – Vendor Service

This is the **Vendor Microservice** for the GadgetHive platform. It manages vendor registration, authentication, and profile management.

## 🛠 Tech Stack
- **Spring Boot**
- **MySQL (AWS RDS)**
- **JWT for authentication**
- **REST APIs**
- **CI/CD**: Jenkins + AWS EC2

## 📦 Features
- Vendor registration
- Login with JWT
- Vendor profile management

## 🔧 Setup Instructions

### 1. Clone the repository
```bash
git clone https://github.com/sanjay872/VendorService.git
cd VendorService
```

### 2. Update application properties
In `src/main/resources/application.properties`:
```bash
spring.datasource.url=jdbc:mysql://<AWS-RDS-ENDPOINT>:3306/VendorService
spring.datasource.username=<your-db-user>
spring.datasource.password=<your-db-password>

spring.jpa.hibernate.ddl-auto=update
server.port=8082
```

### 3. Run locally
```bash
./mvnw spring-boot:run
```
API will be available at http://localhost:8082/.

## 🧪 API Endpoints
`POST /vendors` – Vendor signup
`GET /vendors/{id}` – Vendor details
`GET /vendors` – Vendor all vendors
`DELETE /vendors/{id}` – Vendor delete
`PUT /vendors` – Vendor update

## 🚀 Deployment
- CI/CD with Jenkins
- Deployed on AWS EC2
- Uses AWS RDS (MySQL)
