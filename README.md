# Bank Loan Aplication

Some info

---

## Project Brief

---

The purpose of this project is to build a potential backend system for bank loan application.

The project aims to:

- leverage spring security with secure endpoints,
- register users with roles,
- implement CRUD operations in database,
- send/receive data using REST API

This project omplementats Spring Boot framework with given dependencies:

- Lombok --> Java annotation library which helps to reduce boilerplate code.
- Spring Web --> Builds web, including RESTful, applications using Spring MVC
- Spring Security --> Highly customizable authentication and access-control framework for Spring applications.
- Spring Data JPA --> Persists data in SQL stores with Java Persistence API using Spring Data and Hibernate
- PostgreSQL Driver --> JDBC & R2DBC driver allowing Java programs to connect to PostgreSQL database using standart Java code

---

## API Documentation

[Swagger](to be updated)

To access Swagger API doc, update baseUrl with your local server port.

> {{baseUrl}}/swagger-ui/index.html .

![image](https://user-images.githubusercontent.com/19313466/184607079-d706432f-460f-4323-873d-a95ebef97453.png)

---

## Functional Requirements & Analysis

---

| **USER STORY ID** | **AS A** | **I WANT TO**                                | **SO THAT**                                                          |
|-------------------|----------|----------------------------------------------|----------------------------------------------------------------------|
| 1                 | customer | register to bank loan system                 | I can create a customer account                                      |
| 2                 | customer | add/update/delete loan application           | I can manage my loan application                                     | 
| 3                 | customer | browse my loan application(s)                | I can see my list of confirmed and rejected loan application         | 
| 4                 | customer | login / logout                               | I can securely enter and leave the system                            | 
| 5                 | admin    | register to bank loan system                 | I can perform as a root user such as employee or bank manager        |  
| 6                 | admin    | view a loan application request              | I can review the application information                             |
| 7                 | admin    | confirm / reject                             | I can respond the application                                        |
| 8                 | admin    | add / update / delete a customer of the bank | I can manage customer records                                        |
| 9                 | admin    | send sms to customer                         | I can notify customer about the outcome of his/her loan application  |  
| 10                | admin    | login & logout                               | I can securely enter and leave the system                            |

---

## Design Use-Case Diagram

---

### Use-Case Diagram

---

![](to be updated)

---

### ERD Database Design

---

![](https://github.com/gulbalasalamov/bank-loan-application/blob/master/doc/loan-application-erd.png)

---
