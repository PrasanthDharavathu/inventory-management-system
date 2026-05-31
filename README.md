# Inventory Management System

## Project Overview

Inventory Management System is a full-stack web application developed to manage inventory products efficiently. The application provides REST APIs and a modern React-based user interface for performing CRUD operations, searching, filtering, sorting, pagination, validation, exception handling, and unit testing.

---

## Features

### Backend Features

* Add Product
* Get All Products
* Get Product By ID
* Update Product
* Delete Product
* Search Products
* Category Filtering
* Low Stock Filtering
* Pagination
* Sorting
* DTO Architecture
* Global Exception Handling
* Swagger/OpenAPI Documentation
* Unit Testing with JUnit & Mockito
* Logging using SLF4J

### Frontend Features

* Product Dashboard
* Add Product Form
* Edit Product
* Delete Product
* Search Products by Name
* Filter Products by Category
* Combined Search and Category Filtering
* Dynamic Category Dropdown
* Product Sorting (ID, Name, Category, Price, Quantity)
* Pagination Controls
* Client-Side Validation Messages
* Responsive UI using Bootstrap
* No Products Found message
* Combined Search and Category Filtering
* Dynamic Category Dropdown
* Pagination Controls
* Product Sorting
* Form Validation

---

## Technologies Used

### Backend

* Java 17
* Spring Boot 3
* Spring Data JPA
* MySQL
* Maven
* Swagger/OpenAPI
* JUnit 5
* Mockito

### Frontend

* React
* Vite
* Axios
* Bootstrap
* JavaScript
* HTML
* CSS

### Tools

* Git
* GitHub
* Postman

---

## API Endpoints

### Product APIs

| Method | Endpoint                                                | Description                       |
| ------ | ------------------------------------------------------- | --------------------------------- |
| POST   | /products                                               | Create Product                    |
| GET    | /products                                               | Get All Products                  |
| GET    | /products/{id}                                          | Get Product By ID                 |
| PUT    | /products/{id}                                          | Update Product                    |
| DELETE | /products/{id}                                          | Delete Product                    |
| GET    | /products/search?keyword=value                          | Search Products                   |
| GET    | /products/category/{category}                           | Get Products By Category          |
| GET    | /products/low-stock                                     | Get Low Stock Products            |
| GET    | /products/page?page=0&size=5&sortBy=price&direction=asc | Pagination & Sorting              |
| GET    | /products/search/page                                   | Search with Pagination            |
| GET    | /products/category/{category}/page                      | Category Filter with Pagination   |
| GET    | /products/filter                                        | Combined Search + Category Filter |

---

## Pagination Example

```http
GET /products/page?page=0&size=5
```

## Sorting Example

```http
GET /products/page?page=0&size=5&sortBy=price&direction=asc
```

## Combined Filter Example

```http
GET /products/filter?keyword=samsung&category=Electronics&page=0&size=5&sortBy=price&direction=asc
```

---

## Running the Application

### Backend

```bash
mvn spring-boot:run
```

### Frontend

```bash
cd frontend
npm install
npm run dev
```

---

## Swagger Documentation

```text
http://localhost:8080/swagger-ui/index.html
```

---

## Future Enhancements

* GitHub Actions CI/CD
* Docker Containerization
* Authentication & Authorization
* Dashboard Analytics
* Inventory Reports
