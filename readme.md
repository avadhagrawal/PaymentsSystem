# Payment Webhook Application

A Spring Boot application that processes payments and delivers webhooks reliably with retry and failure handling mechanisms.

---

## 🚀 Features

* Create and process payments
* Register webhook endpoints
* Asynchronous webhook delivery
* Retry mechanism with exponential backoff
* Failure handling with dead-letter state
* OpenAPI specification for API documentation

---

## ▶️ How to Run Locally

### 1. Clone the repository

```
git clone <your-repo-url>
cd project-root
```

---

### 2. Build the project

```
mvn clean install
```

---

### 3. Run the application

```
mvn spring-boot:run
```

Application will start at:

```
http://localhost:8080
```

---

## 📬 API Endpoints
![]("C:\Users\HP\Downloads\Screenshot 2026-03-18 105948.png")
![]("C:\Users\HP\Downloads\Screenshot 2026-03-18 105842.png")