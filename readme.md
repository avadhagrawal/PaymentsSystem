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

### Create Payment

```
POST /payments
```

Sample request:

```json
{
  "amount": 100.50,
  "status": "SUCCESS"
}
```

---

### Register Webhook

```
POST /webhooks
```

```json
{
  "url": "http://localhost:9000/webhook"
}
```

---

### List Webhooks

```
GET /webhooks
```

---

### Get Delivery Status

```
GET /deliveries
```

---

## 🔁 Webhook Flow

1. Payment is created
2. Active webhooks are fetched
3. WebhookDelivery entry is created
4. Webhook is sent asynchronously
5. Retries happen on failure
6. Final state is marked as:

    * SUCCESS
    * FAILED (during retries)
    * DEAD (after max retries)

---

## 🔐 Headers Used

| Header      | Description                           |
| ----------- | ------------------------------------- |
| X-Event-ID  | Unique event identifier (idempotency) |
| X-Signature | Payload signature for verification    |

---

## 📄 OpenAPI Specification

The API contract is available at:

```
openapi.yaml
```

You can visualize it using:

```
npx @redocly/cli preview-docs openapi.yaml
```

---

## 🧪 Testing Webhooks Locally

You can use a simple HTTP server to test webhook delivery:

```
npx http-server -p 9000
```

Or use tools like:

* Postman Mock Server
* RequestBin / Webhook.site

---

## ⚠️ Error Handling

* 400 → Invalid request
* 500 → Internal server error
* Retry attempts → 3 times before marking as DEAD

---

## 📌 Design Considerations

* Idempotency via `X-Event-ID`
* Retry using Spring Retry
* Async processing using `@Async`
* Separation of concerns (dispatcher vs sender)
* Delivery persistence for auditability

---

## 🚀 Future Improvements

* Exponential backoff strategy
* Dead-letter queue (Kafka/RabbitMQ)
* Webhook signature verification endpoint
* Rate limiting
* Authentication & authorization
* API versioning

---

## 👨‍💻 Author

Built as part of a backend engineering coding challenge.

---
