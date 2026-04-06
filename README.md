# Finance Data Processing Backend

## Overview

This project is a backend system designed for Financial Data Management, built using **Spring Boot**, **MySQL**, and **JWT-based authentication**.

It allows users to:
- Manage financial transactions (income & expenses)
- View dashboard summaries
- Enforce role-based access control (`ADMIN`, `ANALYST`, `VIEWER`)
- Secure APIs using JWT authentication

## Tech Stack

- **Java 17+**
- **Spring Boot**
- **Spring Security**
- **JWT (JSON Web Token)**
- **Spring Data JPA (Hibernate)**
- **MySQL**
- **Lombok**

## Project Structure

text
com.example.financedp 
│ 
├── config       # Security configuration 
├── controller   # REST APIs 
├── service      # Business logic 
├── repository   # Database access 
├── model        # Entities & enums 
├── security     # JWT + UserDetails 
├── middleware   # Activity tracking filter 
└── exception    # Custom exceptions


## Roles

- **ADMIN**: Full Access
- **ANALYST**: View Records and Access Insights
- **VIEWER**: View Only Dashboard data

## Business Logic

- **Viewer** → sees only their own data
- **Admin / Analyst** → sees all data
- **Dashboard calculates**:
  - Total Income
  - Total Expense
  - Balance
  - Category-wise summary

## API Reference

### Dashboard API

**GET** `/dashboard`

**Response:**

json
{
  "TotalIncome": 5000,
  "TotalExpense": 2000,
  "Balance": 3000,
  "ExpenditureSummary": {
    "Food": 1000,
    "Travel": 1000
  }
}


## Future Improvements

- [ ] Pagination & filtering
- [ ] Refresh tokens
- [ ] Swagger documentation

## License

This project is for demo purposes.
