# Bank Loan Aplication

This app is a demonstration for a loan application system. 

Verified users can register as bank customer and apply to loan. 

The bank loan management system would evaluate their application according to custom bank loan criteria and respond customers if they are available to receive loan, and their loan limit. 

To do so, use the CustomerAPI and LoanApplicationAPI for the workflow.

The LoanApi and NotificationAPI is for internal use.

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

To access Swagger doc, click live link below: 

https://bank-loan-application-demo.herokuapp.com/swagger-ui/index.html

You can also asccess it locally by updating baseUrl with your local server port.

> {{baseUrl}}/swagger-ui/index.html .

![image](https://user-images.githubusercontent.com/19313466/184607079-d706432f-460f-4323-873d-a95ebef97453.png)


POST http://localhost:8086/api/v1/customer/add

Following request will create a bank customer record in the database. 

```json
{
    "nationalIdentityNumber": "93111111111",
    "firstName": "{{$randomFullName}}",
    "lastName": "{{$randomLastName}}",
    "phone": "{{$randomPhoneNumber}}",
    "email": "{{$randomEmail}}",
    "monthlyIncome": "10000",
    "gender": "male",
    "age": "{{$randomInt}}",
    "loanScore":"1000"
}
```

GET http://localhost:8086/api/v1/customer/get/{nationalIdentityNumber}

Following request will return the bank customer with specified national identity number. 


```json
{
    "nationalIdentityNumber": "93111111111",
    "firstName": "Isaac Terry",
    "lastName": "Baumbach",
    "phone": "947-893-1929",
    "email": "Leon76@hotmail.com",
    "monthlyIncome": 10000.0,
    "gender": "male",
    "age": 661,
    "loanScore": 1000,
    "loanApplications": []
}
```

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

### Demonstration

![image](https://user-images.githubusercontent.com/19313466/184616627-214583d5-92c5-4d6d-b16a-80025e01a6a5.png)
