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

Sample error response:
```json
{
  "message": "Car is already booked for the specified date range (2024-01-01 to 2024-01-05).",
  "status": 409,
  "error": "CONFLICT",
  "timeStamp": "2024-01-01T12:00:00Z"
}

📂 API Endpoints
Base URL: http://localhost:8080/api | Format: JSON | Security: Extend with JWT for production.

👥 User Management
Secure profiles with email uniqueness and role assignment.

Method

Endpoint

Purpose

Security Notes

POST

/users/register

Register new user (hashes password)

Public

GET

/users/{id}

Get user by ID

Auth Required

GET

/users

List all users

Admin Only

PUT

/users/{id}

Update user (email, password, firstName)

Auth Required

DELETE

/users/{id}

Delete user by ID

Admin Only

📍 Location Management
Handle global rental spots (unique by name + address).

Method

Endpoint

Purpose

Security Notes

POST

/locations

Create new location

Auth Required

GET

/locations/{id}

Get location by ID

Public

GET

/locations

List all locations

Public

PUT

/locations/{id}

Update location (partial)

Auth Required

DELETE

/locations/{id}

Delete location by ID

Admin Only

🚙 Car Management
Inventory with status and pricing (link to locations).

Method

Endpoint

Purpose

Security Notes

POST

/cars

Create new car (auto: "Available")

Auth Required

GET

/cars/{licensePlate}

Get car by license plate

Public

GET

/cars

List all cars

Public

PUT

/cars/{licensePlate}

Update car (rate, status, location)

Auth Required

DELETE

/cars/{licensePlate}

Delete car by license plate

Admin Only

📅 Booking Management
Reservations with overlap checks and auto-pricing.

Method

Endpoint

Purpose

Security Notes

POST

/bookings

Create booking (check availability, calc price)

Auth Required

GET

/bookings/{id}

Get booking by ID

Auth Required

GET

/bookings

List all bookings

Admin Only

PUT

/bookings/{id}

Update booking (dates/status; re-check overlaps)

Auth Required

DELETE

/bookings/{id}

Delete/cancel booking

Auth Required

📝 Example Requests
User Registration

Run
Copy code
POST /api/users/register
Content-Type: application/json

{
  "email": "user@rent.com",
  "password": "SecurePass123",
  "firstName": "Alex"
}
Response (201): {"id":1,"email":"user@rent.com","role":"User ","firstName":"Alex"}

Create Booking (with Overlap Check)

Run
Copy code
POST /api/bookings
Authorization: Bearer <JWT>  // Future: Add JWT
Content-Type: application/json

{
  "userId": 1,
  "carId": 1,
  "startDate": "2024-01-15",
  "endDate": "2024-01-20"
}
Response (201): {"id":1,"userId":1,"carId":1,"startDate":"2024-01-15","endDate":"2024-01-20","bookingStatus":"Booked","totalPrice":250.00}

Error Example (Overlap): 409 Conflict with message about unavailable dates.

Get All Cars

Run
Copy code
GET /api/cars
Response (200): [{"id":1,"licensePlate":"ABC-123","dailyRate":50.00,"carStatus":"Available","currentLocationId":1,"currentLocationName":"Downtown"}]

