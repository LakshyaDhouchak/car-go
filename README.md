🚗 Car-Go: High-Integrity Car Rental Backend API
This is a Spring Boot 3 application designed for managing vehicle inventory, logistics (Locations), and core transactional bookings. It features robust exception handling, advanced data integrity checks, and a clean REST API design.

🌟 Key Features
✅ Zero-Conflict Booking System (Core Achievement): Implements specialized JPQL queries to ensure absolute data integrity by preventing overlapping car reservations in a concurrent environment. This logic differentiates between creating new bookings (3-parameter check) and updating existing ones (4-parameter check).
📝 Car & Inventory Management: Full CRUD operations for vehicle records, including tracking current CarStatus (AVAILABLE, IN_USE).
💪 Booking Lifecycle Management: Handles critical state transitions: RESERVED → IN_USE (Pick-up) → COMPLETED (Return).
📬 User Management: Supports user registration and comprehensive profile management (CRUD).
🌍 Location Management: CRUD operations for rental pickup and drop-off points.
⚠️ Centralized Exception Handling: Consistent, standardized error responses for all API calls.

⚠️ Exception Handling
  All APIs implement consistent exception management using:
  
  CarUnavailableException: Custom business exception covering booking date overlaps or attempts to book unavailable cars.
  
  ResourceNotFoundException: Handles queries for non-existent entities (Car, User, Booking).
  
  InvalidInputException: Covers validation issues (e.g., date logic, invalid status transitions).
  
  Centralized handling ensures:
  
  Clear error codes and messages.
  
  Standard error response format.
  
  HTTP status mapped to error type (e.g., 400, 404, 409 Conflict for booking overlaps).
  
  Sample Error Response (409 Conflict)
  {
    "timestamp": "YYYY-MM-DDTHH:MM:SS",
    "status": 409,
    "error": "Conflict",
    "message": "Car is already booked for the specified date range.",
}
