# **Customer Feedback Service**

A **Spring Boot** microservice that allows users to leave feedback on different **places** (e.g., restaurants, shops).  
Uses **Spring Security (Basic Authentication)**, **Spring Data JPA**, **Flyway**, and **PostgreSQL**.

---

## **🚀 Features**
- **User Authentication** (Basic Auth with email & password)
- **Feedback System** (Only one feedback per user per place)
- **Supports Multiple Place Types** (`restaurant`, `shop`, etc.)
- **PostgreSQL Database** (with **Flyway** migrations)
- **Spring Boot REST API** (fully secured)

---

## **📌 Tech Stack**
- **Java 23**
- **Spring Boot 3.4+**
- **Spring Security (Basic Auth)**
- **Spring Data JPA**
- **PostgreSQL**
- **Flyway for DB migrations**

---

## **📦 Project Setup**

### **1️⃣ Clone the Repository**
```sh
git clone https://github.com/Jambit-am/customer-feedback-service.git
cd customer-feedback-service
```

### **2️⃣ Run PostgreSQL Using Docker**
```sh
docker run --name feedback-db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=feedback_db -p 5432:5432 -d postgres
```

### **3️⃣ Configure `application.properties`**
Edit `src/main/resources/application.properties` if needed:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/feedback_db
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver
```

### **4️⃣ Build and Run the Application**
```sh
mvn clean install
mvn spring-boot:run
```

---

## **📂 API Endpoints**

### **🛠️ User Authentication**
#### **🔹 Register a New User**
```sh
curl -X POST http://localhost:8080/api/auth/register \
     -H "Content-Type: application/json" \
     -d '{
           "email": "user@example.com",
           "password": "password123"
         }'
```

### **🏛️ Places**
#### **🔹 Get All Places**
```sh
curl -X GET http://localhost:8080/api/places
```

### **💬 Feedback**
#### **🔹 Submit Feedback (Authenticated User)**
```sh
curl -X POST http://localhost:8080/api/feedbacks \
     -u user@example.com:password123 \
     -H "Content-Type: application/json" \
     -d '{
           "title": "Awesome Pizza!",
           "comment": "Loved the pizza.",
           "score": 9,
           "placeId": 1 
         }'
```

#### **🔹 Get Feedbacks for Place**
```sh
curl -X GET http://localhost:8080/api/places/1/feedbacks
```

---

## **📜 Database Schema (Flyway)**
### **Tables Created**
- **users** (`id`, `email`, `password`, `create_time`, `last_update_time`)
- **places** (`id`, `name`, `type`, `create_time`)
- **feedbacks** (`id`, `title`, `comment`, `score`, `create_time`, `user_id`, `place_id`)

### **Default Data (`V2__Insert_Default_Data.sql`)**
- **Users:** `user1@example.com`, `user2@example.com`
- **Places:** `Pizza Palace (restaurant)`, `Tech Store (shop)`
- **Feedback:** Users have already left some feedback.

---

## **📌 Notes**
- **Passwords are hashed using BCrypt**, do not store plain-text passwords.
- **Each user can submit only one feedback per place**.
- **Database migrations are handled automatically by Flyway**.
