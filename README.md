**Finance Data Processing Backend (Spring Boot + JWT)**
**Overview**
This Project is a backend system made for Financial Data Management built using SpringBoot, MySQL and JWT-based authentication

It allows user to:
1. Manage financial transactions (income & expenses)
2. View dashboard summaries
3. Enforce role-based access control (ADMIN, ANALYST, VIEWER)
4. Secure APIs using JWT authentication

Tech Stack:
1. Java 17+
2. Spring Boot
3. Spring Security
4. JWT (JSON Web Token)
5. Spring Data JPA (Hibernate)
6. MySQL
7. Lombok

**Project Structure**
com.example.financedp 
│ 
├── config       # Security configuration 
├── controller   # REST APIs 
├── service      # Business logic 
├── repository   # Database access 
├── model        # Entities & enums 
├── security     # JWT + UserDetails 
├── middleware   # Activity tracking filter 
├── exception    # Custom exceptions

**Roles**
1. ADMIN - Full Access
2. ANALYST - View Records and Access Insights
3. VIEWER - View Only DashBoard data

**Buisness Logic**

1. Viewer → sees only their own data
2. Admin / Analyst → sees all data
3. Dashboard calculates:
    1. Total Income
    2. Total Expense
    3. Balance
    4. Category-wise summary

**Dashboard API**
GET /dashboard

Response:

{
  "TotalIncome": 5000,
  "TotalExpense": 2000,
  "Balance": 3000,
  "ExpenditureSummary": {
    "Food": 1000,
    "Travel": 1000
    }
}

**Future Improvements**
1. Pagination & filtering
2. Refresh tokens
3. Swagger documentation

**License**

This project is for demo purposes.
