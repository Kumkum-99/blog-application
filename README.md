# blog-application

A Spring Boot–based blog platform with authentication, role-based access control, and features like posts, comments, and reactions (likes).  
Built with **Spring Boot 3**, **Spring Security (JWT)**, **Hibernate/JPA**, and **MySQL**.
## Features

- User Registration & Login (JWT-based authentication)
- Role-based access (USER, ADMIN)
- CRUD operations on blog posts
- Add/Delete comments
- Like/Unlike posts
- Swagger UI for API documentation
- Pagination & Search for posts


## Tech Stack
- **Backend:** Spring Boot 3, Spring Security, JWT
- **Database:** MySQL 
- **ORM:** Hibernate / Spring Data JPA
- **Validation:** Jakarta Bean Validation
- **API Docs:** Springdoc OpenAPI (Swagger)


## 1. Setup Instructions
```
git clone https://github.com/Kumkum-99/blog-application.git
cd blog-application
```
##  1.2 Configure MySQL
Create the database:
```
CREATE DATABASE blogdb;
```


Update src/main/resources/application.properties:
```
spring.datasource.url=jdbc:mysql://localhost:3306/blogdb
spring.datasource.username=yourusername
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```


## 1.3 Build the Project
```
mvn clean install
```

## 1.4 Run the Application
```
mvn spring-boot:run
```
## 2. API Documentation
```
http://localhost:8080/swagger-ui/index.html
```
## 3. Authentication & JWT
## 3.1 Register User
```
POST http://localhost:8080/public/api/registration
```

## Request Body
``` json
{
  "name": "abc xyz",
  "email": "abc@.com",
  "password": "abcxyz123",
 
}
```
## Response:
``` json
{
  "email":"abc@.com",
  "message": "User registered successfully"
}
```
## 3.2 Login to Get JWT
```
POST http://localhost:8080/public/api/login
```
``` json
{
 "email": "abc@.com",
 "password": "abcxyz123",
}
```
## Successful Response:
``` json
{
  "token": "<JWT_TOKEN_HERE>",
  "type": "Bearer",
  "role": "USER"
}
```






