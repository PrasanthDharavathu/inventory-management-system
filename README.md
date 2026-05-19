# Inventory Management System

## Project Overview
This is a Spring Boot backend application developed for managing inventory products. The project provides REST APIs for performing CRUD operations, searching products, pagination, sorting, validation, exception handling, and unit testing.

---

## Features
- Add Product
- Get All Products
- Get Product By ID
- Update Product
- Delete Product
- Search Products
- Category Filtering
- Low Stock Filtering
- Pagination
- Sorting
- DTO Architecture
- Global Exception Handling
- Swagger/OpenAPI Documentation
- Unit Testing with JUnit & Mockito
- Logging using SLF4J

---

## Technologies Used
- Java 17
- Spring Boot 3
- Spring Data JPA
- MySQL
- Maven
- Swagger/OpenAPI
- JUnit 5
- Mockito
- Git & GitHub

---

## API Endpoints

### Product APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /products | Create Product |
| GET | /products | Get All Products |
| GET | /products/{id} | Get Product By ID |
| PUT | /products/{id} | Update Product |
| DELETE | /products/{id} | Delete Product |
| GET | /products/search?keyword=value | Search Products |
| GET | /products/category/{category} | Get Products By Category |
| GET | /products/low-stock | Get Low Stock Products |
| GET | /products/page?page=0&size=5&sortBy=price&direction=asc | Get Paginated and Sorted Products |
---

## Pagination Example

```http
GET /products?page?page=0&size=5
```

## Sorting Example

```http
GET /products/page?page=0&size=5&sortBy=price&direction=asc
```

## Swagger Documentation
```http
http://localhost:8080/swagger-ui/index.html


