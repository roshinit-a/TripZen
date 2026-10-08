-- ============================================
-- TripZen Database Setup Script
-- Run this in MySQL Workbench
-- ============================================

-- Create the database
CREATE DATABASE IF NOT EXISTS tripzen_db;
USE tripzen_db;

-- ============================================
-- TABLE: users
-- ============================================
CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) DEFAULT 'USER',
    phone VARCHAR(15),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- TABLE: destinations
-- ============================================
CREATE TABLE IF NOT EXISTS destinations (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    country VARCHAR(100) DEFAULT 'India',
    state VARCHAR(100),
    description TEXT,
    image_url VARCHAR(500),
    best_time_to_visit VARCHAR(100)
);

-- ============================================
-- TABLE: hotels
-- ============================================
CREATE TABLE IF NOT EXISTS hotels (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(150) NOT NULL,
    location VARCHAR(150),
    price_per_night DOUBLE,
    rating DOUBLE,
    available_rooms INT,
    description TEXT,
    image_url VARCHAR(500),
    destination_id BIGINT,
    FOREIGN KEY (destination_id) REFERENCES destinations(id) ON DELETE SET NULL
);

-- ============================================
-- TABLE: travel_packages
-- ============================================
CREATE TABLE IF NOT EXISTS travel_packages (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(150) NOT NULL,
    description TEXT,
    duration_days INT,
    price DOUBLE,
    image_url VARCHAR(500),
    inclusions TEXT,
    max_persons INT DEFAULT 10,
    is_active BOOLEAN DEFAULT TRUE,
    destination_id BIGINT,
    FOREIGN KEY (destination_id) REFERENCES destinations(id) ON DELETE SET NULL
);

-- ============================================
-- TABLE: bookings
-- ============================================
CREATE TABLE IF NOT EXISTS bookings (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    package_id BIGINT NOT NULL,
    travel_date DATE NOT NULL,
    persons INT NOT NULL,
    total_price DOUBLE NOT NULL,
    status VARCHAR(30) DEFAULT 'PENDING',
    special_requests TEXT,
    booked_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (package_id) REFERENCES travel_packages(id) ON DELETE CASCADE
);

-- ============================================
-- TABLE: payments
-- ============================================
CREATE TABLE IF NOT EXISTS payments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    booking_id BIGINT UNIQUE NOT NULL,
    amount DOUBLE NOT NULL,
    payment_method VARCHAR(30),
    payment_status VARCHAR(30) DEFAULT 'PENDING',
    transaction_id VARCHAR(100),
    paid_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES bookings(id) ON DELETE CASCADE
);

-- ============================================
-- SAMPLE DATA
-- ============================================

-- Admin user (password: admin123 - bcrypt encoded)
INSERT INTO users (name, email, password, role, phone) VALUES
('Admin User', 'admin@tripzen.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBpHfBgMEjdTbG', 'ADMIN', '9000000000'),
('Deepak Kumar', 'user@tripzen.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBpHfBgMEjdTbG', 'USER', '9876543210');
-- Note: Both passwords are 'password123' - change in production!

-- Destinations
INSERT INTO destinations (name, country, state, description, image_url, best_time_to_visit) VALUES
('Goa', 'India', 'Goa', 'Beautiful beaches, vibrant nightlife and Portuguese heritage architecture. Famous for its golden sands, water sports, seafood cuisine and relaxed vibe.', 'https://images.unsplash.com/photo-1512343879784-a960bf40e7f2?w=800', 'October–March'),
('Manali', 'India', 'Himachal Pradesh', 'Snow-capped mountains, adventure sports and scenic valleys. Ideal for skiing, snowboarding, trekking and experiencing local Himachali culture.', 'https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=800', 'April–June'),
('Jaipur', 'India', 'Rajasthan', 'The Pink City with royal palaces, forts and rich Rajasthani culture. Home to Amber Fort, City Palace and vibrant bazaars.', 'https://images.unsplash.com/photo-1477587458883-47145ed94245?w=800', 'November–February'),
('Kerala', 'India', 'Kerala', 'God\'s own country with serene backwaters, lush greenery, spice plantations and Ayurvedic traditions.', 'https://images.unsplash.com/photo-1593693397690-362cb9666fc2?w=800', 'September–March'),
('Darjeeling', 'India', 'West Bengal', 'Famous for its tea gardens, the iconic toy train ride, stunning Kanchenjunga views and colonial charm.', 'https://images.unsplash.com/photo-1571115177098-24ec42ed204d?w=800', 'March–May'),
('Udaipur', 'India', 'Rajasthan', 'City of Lakes with stunning palaces, romantic boat rides and traditional Rajasthani art and culture.', 'https://images.unsplash.com/photo-1599661046289-e31897846e41?w=800', 'October–March');

-- Hotels
INSERT INTO hotels (name, location, price_per_night, rating, available_rooms, description, image_url, destination_id) VALUES
('The Grand Goa Resort', 'Baga Beach, North Goa', 4500.0, 4.5, 8, 'Luxury beachside resort with stunning ocean views, infinity pool and world-class dining.', 'https://images.unsplash.com/photo-1571003123894-1f0594d2b5d9?w=600', 1),
('Sunshine Beach Inn', 'Calangute, Goa', 2200.0, 3.8, 15, 'Budget-friendly hotel just 5 minutes walk from the beach with clean comfortable rooms.', 'https://images.unsplash.com/photo-1566073771259-6a8506099945?w=600', 1),
('Manali Snow Peak Hotel', 'Mall Road, Manali', 3200.0, 4.2, 12, 'Cozy mountain hotel with panoramic Himalayan views and warm hospitality.', 'https://images.unsplash.com/photo-1551882547-ff40c63fe5fa?w=600', 2),
('Jaipur Palace Inn', 'Civil Lines, Jaipur', 2800.0, 4.0, 20, 'Heritage hotel with traditional Rajasthani decor, rooftop restaurant and palace views.', 'https://images.unsplash.com/photo-1564501049412-61c2a3083791?w=600', 3),
('Kerala Backwater Villa', 'Alleppey, Kerala', 5500.0, 4.8, 5, 'Premium villa with direct backwater access, kayaking, and authentic Kerala cuisine.', 'https://images.unsplash.com/photo-1578683010236-d716f9a3f461?w=600', 4),
('Darjeeling Tea Garden Resort', 'Darjeeling Hills', 3800.0, 4.3, 10, 'Surrounded by lush tea gardens with stunning mountain views and heritage toy train nearby.', 'https://images.unsplash.com/photo-1523592121529-f6dde35f079e?w=600', 5);

-- Travel Packages
INSERT INTO travel_packages (name, description, duration_days, price, image_url, inclusions, max_persons, is_active, destination_id) VALUES
('Goa Beach Explorer', 'Enjoy 4 days of sun, sand and seafood in beautiful Goa. Visit beautiful beaches like Baga, Calangute and Anjuna. Experience the vibrant nightlife, savour delicious Goan cuisine and explore the old Portuguese churches.', 4, 15000.0, 'https://images.unsplash.com/photo-1512343879784-a960bf40e7f2?w=800', 'Hotel Stay (3 Star),Daily Breakfast,Airport Transfer,Beach Tour,Sightseeing Guide,Water Sports', 10, TRUE, 1),
('Goa Premium Escape', 'Luxury 6-day Goa holiday with 5-star resort stay, private beach cabana, sunset cruise and candlelight dinner.', 6, 30000.0, 'https://images.unsplash.com/photo-1507525428034-b723cf961d3e?w=800', '5-Star Hotel Stay,All Meals,Sunset Cruise,Private Beach Cabana,Spa Session,Airport Transfer', 6, TRUE, 1),
('Manali Snow Adventure', '5 days of adventure in the snow-clad mountains of Manali. Experience skiing, snowboarding and river rafting. Visit Rohtang Pass, Solang Valley and Hadimba Temple.', 5, 20000.0, 'https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=800', 'Hotel Stay (4 Star),All Meals,Skiing/Snowboarding,River Rafting,Rohtang Pass Visit,Hadimba Temple', 8, TRUE, 2),
('Jaipur Royal Heritage', 'Explore the Pink City palaces and vibrant Rajasthani culture over 3 days. Visit Amber Fort, City Palace, Hawa Mahal and enjoy a traditional cultural show.', 3, 12000.0, 'https://images.unsplash.com/photo-1477587458883-47145ed94245?w=800', 'Hotel Stay,Daily Breakfast,Amber Fort Entry,City Palace Tour,Cultural Show,Desert Safari', 12, TRUE, 3),
('Kerala Backwater Bliss', 'Cruise through serene backwaters, enjoy Ayurvedic spa and relish authentic Kerala cuisine over 6 days. Includes houseboat stay on Alleppey backwaters.', 6, 25000.0, 'https://images.unsplash.com/photo-1593693397690-362cb9666fc2?w=800', 'Houseboat Stay,All Meals,Backwater Cruise,Ayurvedic Spa,Kathakali Show,Spice Plantation Visit', 8, TRUE, 4),
('Darjeeling Tea Trail', 'Experience 4 days of tea gardens, toy train ride, Kanchenjunga sunrise view and colonial Darjeeling charm.', 4, 18000.0, 'https://images.unsplash.com/photo-1571115177098-24ec42ed204d?w=800', 'Hotel Stay,Daily Breakfast,Toy Train Ride,Tea Garden Tour,Tiger Hill Sunrise,Himalayan Museum', 10, TRUE, 5),
('Udaipur Lake Romance', 'Romantic 3-day getaway in the City of Lakes with heritage hotel stay, palace boat ride and stunning sunset views.', 3, 14000.0, 'https://images.unsplash.com/photo-1599661046289-e31897846e41?w=800', 'Heritage Hotel Stay,Breakfast,Boat Ride on Lake Pichola,City Palace Tour,Cultural Dinner,Sunset View', 8, TRUE, 6);

-- ============================================
-- END OF SCRIPT
-- ============================================
SELECT 'TripZen database setup complete!' AS message;
