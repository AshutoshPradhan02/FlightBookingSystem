CREATE DATABASE IF NOT EXISTS flightdb;
USE flightdb;

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    role ENUM('admin', 'local') NOT NULL DEFAULT 'local',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY unique_username_role (username, role)
);

CREATE TABLE IF NOT EXISTS flights (
    id INT AUTO_INCREMENT PRIMARY KEY,
    from_city VARCHAR(100) NOT NULL,
    to_city VARCHAR(100) NOT NULL,
    time VARCHAR(50) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    UNIQUE KEY unique_flight_schedule (from_city, to_city, time)
);

CREATE TABLE IF NOT EXISTS bookings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    flight_id INT NOT NULL,
    booked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_bookings_user
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_bookings_flight
        FOREIGN KEY (flight_id) REFERENCES flights(id)
        ON DELETE CASCADE
);

INSERT INTO users (username, password, role)
VALUES ('admin', 'admin123', 'admin')
ON DUPLICATE KEY UPDATE username = username;

INSERT INTO flights (from_city, to_city, time, price)
VALUES
    ('Delhi', 'Mumbai', '09:00 AM', 4500.00),
    ('Bengaluru', 'Hyderabad', '11:30 AM', 3200.00),
    ('Kolkata', 'Chennai', '06:45 PM', 5100.00)
ON DUPLICATE KEY UPDATE from_city = from_city;
