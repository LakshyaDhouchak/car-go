<div align="center">

# 🚗 Car-Go: Ultimate Car Rental Backend API

[![Java](https://img.shields.io/badge/Java-21-blue.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-green.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0%2B-orange.svg)](https://www.mysql.com/)
[![Maven](https://img.shields.io/badge/Maven-3.6%2B-red.svg)](https://maven.apache.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

**A robust Spring Boot backend for car rental management. Handle users, locations, cars, and bookings with smart overlap detection, secure hashing, and clean REST APIs!**


> *Power your car rental business with seamless bookings, real-time availability, and scalable backend magic. From user sign-ups to conflict-free reservations – drive ahead!*

</div>

---

## 🌟 Key Features

- 🔐 **User  Management**: Secure registration with BCrypt password hashing (default "User " role; extend for JWT auth).
- 📍 **Location Handling**: Manage pickup/drop-off spots (unique by name + address) for global scalability.
- 🚙 **Car Inventory**: Track vehicles with license plates, daily rates, status (e.g., "Available"), and location links.
- 📅 **Smart Bookings**: CRUD operations with automatic price calculation (rate × days), overlap detection, and status updates (e.g., "Booked", "Reserved").
- ⚠️ **Robust Exception Handling**: Centralized errors for not found, conflicts (e.g., car unavailable), and invalid inputs.
- 🛡️ **Validation & Security**: DTO-based input validation (`@NotNull`, `@Email`, `@Future` dates) + Spring Security basics.
- 📊 **Extensible Design**: Flyway migrations, JPA repositories, and services ready for pagination, auth enhancements, or payments.

---

## ⚠️ Exception Handling

All APIs use a **centralized global exception handler** (`GlobalExceptionHandler`) for consistent error management:

- **`ResourceNotFoundException`**: For missing entities (e.g., invalid ID) → 404.
- **`CarUnavailableException` / `DataConflictException`**: For overlaps/duplicates → 409.
- **`InvalidInputException`**: For bad data (e.g., past dates) → 400.
- **`SystemException`**: Catches unexpected errors → 500.

This ensures:
- Clear, user-friendly messages.
- Standardized JSON responses with timestamp, status, and error type.
- No stack traces leaked to clients.


📂 API Endpoints
Base URL: http://localhost:8080/api | Format: JSON | Security: Extend with JWT for production.

Sample error response:
```json
{
  "message": "Car is already booked for the specified date range (2024-01-01 to 2024-01-05).",
  "status": 409,
  "error": "CONFLICT",
  "timeStamp": "2024-01-01T12:00:00Z"
}
```

##📂 API Endpoints

Base URL: http://localhost:8080/api
###👥 User Management

Method  |  Endpoint            |     Purpose                                    |         Security Notes

----------------------------------------------------------------------------------------------------------------

POST    |   /users/register   |   Register new user (hashes password)            |       Public

GET     |   /users/{id}       |   Get user by ID                                 |       Auth Required (JWT)

GET     |   /users            |   List all users                                 |       Admin Only (Role Check)

PUT     |   /users/{id}       |   Update user (email, password, firstName)       |       Auth Required (Own User)

DELETE  |   /users/{id}       |   Delete user by ID                              |       Admin Only





###📍 Location Management

Method   | Endpoint            |   Purpose                                       |        Security Notes

-----------------------------------------------------------------------------------------------------------------

POST     | /locations          |  Create new location                            |      Auth Required (Admin)

GET      | /locations/{id}     |  Get location by ID                             |      Public

GET      | /locations          |  List all locations                             |      Public

PUT      | /locations/{id}     |  Update location (partial)                      |      Auth Required (Admin)

DELETE   | /locations/{id}     |  Delete location by ID                          |      Admin Only



###🚙 Car Management

Method  |  Endpoint              |    Purpose                                         |     Security Notes

-----------------------------------------------------------------------------------------------------------------

POST    |  /cars                 |   Create new car (auto: "Available")               |      Auth Required (Admin)

GET     |  /cars/{licensePlate}  |   Get car by license plate                         |      Public

GET     |  /cars                 |   List all cars                                    |      Public

PUT     |  /cars/{licensePlate}  |   Update car (rate, status, location)              |      Auth Required (Admin)

DELETE  |  /cars/{licensePlate}  |   Delete car by license plate                      |      Admin Only



###📅 Booking Management

Method  |  Endpoint              |  Purpose                                              |  Security Notes

-----------------------------------------------------------------------------------------------------------------

POST    |  /bookings             |   Create booking (check availability, calc price)      |   Auth Required (User)

GET     |  /bookings/{id}        |   Get booking by ID                                    |   Auth Required (Own/User)

GET     |  /bookings             |   List all bookings                                    |   Admin Only

PUT     |  /bookings/{id}        |   Update booking (dates/status; re-check overlaps)     |   Auth Required (Own)

DELETE  |  /bookings/{id}        |   Delete/cancel booking                                |   Auth Required (Own)



