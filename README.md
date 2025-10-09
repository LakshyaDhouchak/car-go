<div align="center">

# 🚗 Car-Go: Ultimate Car Rental Backend API

[![Java](https://img.shields.io/badge/Java-21-blue.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-green.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0%2B-orange.svg)](https://www.mysql.com/)
[![Maven](https://img.shields.io/badge/Maven-3.6%2B-red.svg)](https://maven.apache.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

**A robust Spring Boot backend for car rental management. Handle users, locations, cars, and bookings with smart overlap detection, secure hashing, and clean REST APIs!**

![Car-Go Banner](https://via.placeholder.com/800x200/007BFF/FFFFFF?text=Car-Go+API+-+Rent+Smart%2C+Drive+Easy) <!-- Replace with a car-themed image or GIF -->

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

