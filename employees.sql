-- Employee Management System Database Setup
CREATE DATABASE IF NOT EXISTS employee_db;
USE employee_db;

CREATE TABLE IF NOT EXISTS employees (
    emp_number INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    position VARCHAR(255) NOT NULL,
    company VARCHAR(255) NOT NULL,
    residence_card_url VARCHAR(255),
    unified_id_url VARCHAR(255),
    passport_url VARCHAR(255)
);

-- Sample data for testing
INSERT INTO employees VALUES 
(1001, 'John Doe', 'Developer', 'Tech Corp', 'https://images.pexels.com/photos/1234567/pexels-photo-1234567.jpeg', 'https://images.pexels.com/photos/2345678/pexels-photo-2345678.jpeg', 'https://images.pexels.com/photos/3456789/pexels-photo-3456789.jpeg'),
(1002, 'Jane Smith', 'Manager', 'Business Inc', 'https://images.pexels.com/photos/4567890/pexels-photo-4567890.jpeg', 'https://images.pexels.com/photos/5678901/pexels-photo-5678901.jpeg', 'https://images.pexels.com/photos/6789012/pexels-photo-6789012.jpeg');