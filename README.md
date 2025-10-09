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
```

---

## 🔗 API Details

| Detail | Value |
| :--- | :--- |
| **Base URL** | `http://localhost:8080/api` |
| **Data Format** | JSON |
| **Security Note** | Extend with **JSON Web Tokens (JWT)** for production environments to handle authentication. |

---



## 👥 User Management

| Method | Endpoint | Purpose | Security Notes |
| :--- | :--- | :--- | :--- |
| **`POST`** | `/users/register` | Register new user (**hashes password**) | Public |
| **`GET`** | `/users/{id}` | Get user by ID | Auth Required (JWT) |
| **`GET`** | `/users` | List all users | **Admin Only** (Role Check) |
| **`PUT`** | `/users/{id}` | Update user (email, password, firstName) | Auth Required (Own User) |
| **`DELETE`** | `/users/{id}` | Delete user by ID | **Admin Only** |

---

## 📍 Location Management

| Method | Endpoint | Purpose | Security Notes |
| :--- | :--- | :--- | :--- |
| **`POST`** | `/locations` | Create new location | Auth Required (Admin) |
| **`GET`** | `/locations/{id}` | Get location by ID | Public |
| **`GET`** | `/locations` | List all locations | Public |
| **`PUT`** | `/locations/{id}` | Update location (partial) | Auth Required (Admin) |
| **`DELETE`** | `/locations/{id}` | Delete location by ID | **Admin Only** |

---

## 🚙 Car Management

| Method | Endpoint | Purpose | Security Notes |
| :--- | :--- | :--- | :--- |
| **`POST`** | `/cars` | Create new car (**auto: "Available"** status) | Auth Required (Admin) |
| **`GET`** | `/cars/{licensePlate}` | Get car by license plate | Public |
| **`GET`** | `/cars` | List all cars | Public |
| **`PUT`** | `/cars/{licensePlate}` | Update car (rate, status, location) | Auth Required (Admin) |
| **`DELETE`** | `/cars/{licensePlate}` | Delete car by license plate | **Admin Only** |

---

## 📅 Booking Management

| Method | Endpoint | Purpose | Security Notes |
| :--- | :--- | :--- | :--- |
| **`POST`** | `/bookings` | Create booking (check availability, calculate price) | Auth Required (User) |
| **`GET`** | `/bookings/{id}` | Get booking by ID | Auth Required (Own/User) |
| **`GET`** | `/bookings` | List all bookings | **Admin Only** |
| **`PUT`** | `/bookings/{id}` | Update booking (dates/status; **re-check overlaps**) | Auth Required (Own) |
| **`DELETE`** | `/bookings/{id}` | Delete/cancel booking | Auth Required (Own) |

---
---

## 🛠 Project Setup & Quick Start

Get the Car-Go service running on your local machine in minutes!

### Build and Run

1.  **Clone the Repository:**
    ```bash
    git clone [https://github.com/your-org/car-go.git](https://github.com/your-org/car-go.git)
    cd car-go
    ```
2.  **Build and Start:**
    ```bash
    mvn clean install
    mvn spring-boot:run  # Starts on http://localhost:8080
    ```

### ⚙️ Configuration Essentials

Update your database and security settings in `src/main/resources/application.properties`.

| Area | Key Properties | Default Values (Change These!) |
| :--- | :--- | :--- |
| **Database (MySQL)** | `spring.datasource.url` | `jdbc:mysql://localhost:3306/carrentaldb` |
| | `spring.datasource.password` | `lakshya5911` |
| **Security (JWT)** | `jwt.secret` | `yourSecretKey` (Must be strong for production) |
| **Logging** | `logging.level.com.lakshya.car_go` | `DEBUG` (Good for development) |

---

## ⚙️ Database Entities

- ✅ **user**: Core entity for renters/admins (ID, email (unique), hashedPassword, role (e.g., "User  ", "Admin"), firstName, createdAt).
- ✅ **location**: Pickup/drop-off spots (ID, name (unique), address).
- ✅ **car**: Vehicle inventory (ID, licensePlate (unique), dailyRate, carStatus (e.g., "Available", "Booked"), currentLocation (ManyToOne), createdAt).
- ✅ **booking**: Reservations (ID, user (ManyToOne), car (ManyToOne), startDate, endDate, bookingStatus (e.g., "Booked", "Reserved"), totalPrice).

**Relationships & Optimizations**: Booking links User + Car; Car links Location. Indexes on licensePlate, dates, and userId for fast queries. JPA/Hibernate handles lazy loading and cascading deletes.

---

## 🚀 Roadmap

- 📱 **Mobile App Integration**: RESTful APIs ready for React Native or Flutter frontend (e.g., real-time booking search).
- 📈 **Rental Analytics Dashboard**: Admin endpoints for stats (e.g., popular cars, revenue by location, user retention).
- 🔔 **Notification System**: Email/SMS for booking confirmations, reminders, and availability alerts (integrate Twilio/SendGrid).
- 🕒 **Scheduled Tasks**: Background jobs for auto-updating car status (e.g., end of rental → "Available") using Spring @Scheduled.
- 💳 **Payments & Invoicing**: Stripe/PayPal integration for secure transactions and PDF receipts.
- 🌍 **Advanced Features**: Dynamic pricing (e.g., surge during holidays), GPS tracking for cars, multi-language support.

---

## 🤝 Contributing

- 1️⃣ **Fork** the repo and create your own copy.
- 2️⃣ **Create Branch**: `git checkout -b feature/add-payment-integration` (use descriptive names).
- 3️⃣ **Code & Test**: Follow Spring Boot patterns (DTOs, services, repositories); add unit tests with JUnit/Mockito; run `mvn test`.
- 4️⃣ **Commit Changes**: `git commit -m "Add Stripe payment gateway for bookings 💳"`.
- 5️⃣ **Push & PR**: `git push origin feature/add-payment-integration`; Open a Pull Request with details, screenshots, and test cases.

**Guidelines**: Semantic versioning for releases, clean code (no breaking changes without discussion), and always update the README for new features. We welcome ideas for enhancements like loyalty programs!

---

# 🏆 Loyalty Rewards System

## 📘 Overview

The **Loyalty Rewards System** is a gamification feature within the Car-Go API designed to boost user engagement, encourage repeat rentals, and foster long-term customer loyalty. Users earn digital rewards (e.g., points, badges, or discounts) for completing rentals, referring friends, or achieving milestones like safe driving or frequent bookings.

This system leverages the **Strategy Design Pattern**, allowing scalable and maintainable logic by separating reward evaluation from core booking processes. It's perfect for turning one-time renters into loyal customers!

---

## 🎯 Purpose & Goals

The primary goals of the Loyalty Rewards System are:

- 🔁 **Encourage Repeat Rentals**: Reward frequent users with points for each completed booking.
- 🧩 **Promote Profile Completeness**: Bonus points for updating personal details (e.g., adding payment info or preferences).
- 🔥 **Motivate Referral Programs**: Earn rewards for inviting friends who make their first rental.
- 🚗 **Acknowledge Safe & Efficient Use**: Badges for on-time returns or eco-friendly car choices (e.g., electric vehicles).
- 🎉 **Celebrate Milestones**: Discounts or free upgrades for achievements like "10 Rentals" or "1-Year Loyal Renter".

---

## ⚙️ How It Works

When a user completes a tracked action (e.g., finishing a booking), the system dynamically evaluates eligibility for rewards. Each reward type uses a modular strategy to check criteria, making it easy to add new ones without disrupting the codebase.

### Tracked Activities Include:

- ✅ Completing a booking and returning the car on time.
- 🏎️ Accumulating total rental days or distance (if GPS integrated).
- 📞 Referring a new user who books their first rental.
- 📝 Updating or verifying profile information (e.g., adding insurance details).
- 🥇 Reaching loyalty tiers (e.g., Silver: 5 rentals, Gold: 20 rentals).
- 🌿 Opting for sustainable options (e.g., hybrid/electric cars) for green badges.

Each reward has a defined rule set (or "criteria"), and upon qualification, points/badges are automatically assigned and stored in a new `loyalty_rewards` entity.

---

## 🧠 Strategy Pattern Design

The rewards system employs the **Strategy Pattern** for flexible eligibility checks:

- Every reward is tied to a **criteria key** (e.g., "REPEAT_RENTAL", "ON_TIME_RETURN").
- A corresponding **evaluation strategy** (implementing `RewardStrategy` interface) assesses if the user meets the conditions (e.g., query booking history).
- If eligible, the reward is awarded, persisted, and optionally triggers notifications (e.g., "Congrats on your 5th rental! +50 points").

This design ensures clean separation: Add a new strategy class for a reward type without touching existing code, promoting extensibility and testability.

Example Strategy Interface (in code):
```java
public interface RewardStrategy {
    boolean evaluate(User user, Booking booking);  // Or other context
    Reward assign(User user);  // Return points/badge
}
```


