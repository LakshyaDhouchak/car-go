
-- new version
-- User Table:
CREATE TABLE IF NOT EXISTS user(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) UNIQUE NOT NULL,
    hashed_password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    first_name VARCHAR(30) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Location Table:
CREATE TABLE IF NOT EXISTS location(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL,
    address VARCHAR(100) NOT NULL
);

-- Car Table:
CREATE Table IF NOT EXISTS car(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    license_plate VARCHAR(20) UNIQUE NOT NULL,
    daily_rate DECIMAL(10,2) NOT NULL,
    car_status VARCHAR(12) NOT NULL,
    current_location_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(current_location_id) REFERENCES location(id)
);

-- Booking Table:
CREATE TABLE IF NOT EXISTS booking(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    car_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    start_date Date NOT NULL,
    end_date Date NOT NULL,
    booking_status VARCHAR(20) NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY(car_id) REFERENCES car(id),
    FOREIGN KEY(user_id) REFERENCES user(id)
);