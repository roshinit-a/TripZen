# TripZen – Online Travel Booking Platform

> A full-stack college project built with **Spring Boot + MySQL + HTML/CSS/JS**

---

## 📋 Project Overview

**TripZen** is a complete online travel booking platform where users can browse destinations, view hotels, explore travel packages, make bookings and simulate payments. Admins have a full management dashboard.

---

## 🛠️ Tech Stack

| Layer        | Technology                |
|-------------|---------------------------|
|  Language    | Java 17                   |
| Backend     | Spring Boot 3.2           |
| Database    | MySQL 8                   |
| Frontend    | HTML5, CSS3, JavaScript   |
| API Style   | REST API                  |
| Security    | Spring Security + JWT     |
| Build Tool  | Maven                    |

---

## 📁 Project Structure

```
tripzen/
├── backend/                          # Spring Boot backend
│   ├── pom.xml
│   └── src/main/java/com/tripzen/
│       ├── TripZenApplication.java
│       ├── controller/
│       │   ├── AuthController.java
│       │   ├── UserController.java
│       │   ├── DestinationController.java
│       │   ├── HotelController.java
│       │   ├── PackageController.java
│       │   ├── BookingController.java
│       │   ├── PaymentController.java
│       │   └── AdminController.java
│       ├── entity/
│       │   ├── User.java
│       │   ├── Destination.java
│       │   ├── Hotel.java
│       │   ├── TravelPackage.java
│       │   ├── Booking.java
│       │   └── Payment.java
│       ├── repository/          (JPA Repositories)
│       ├── service/             (Business Logic)
│       ├── dto/                 (Request/Response DTOs)
│       └── security/            (JWT + Spring Security)
│
├── frontend/                         # HTML/CSS/JS frontend
│   ├── index.html                    # Home page
│   ├── login.html                    # Login
│   ├── register.html                 # Register
│   ├── destinations.html             # Destinations list
│   ├── hotels.html                   # Hotels list
│   ├── packages.html                 # Packages list
│   ├── package-details.html          # Package detail + booking sidebar
│   ├── booking.html                  # Booking form
│   ├── payment.html                  # Payment page
│   ├── booking-confirmation.html     # Success page
│   ├── my-bookings.html             # User's bookings
│   ├── css/style.css
│   ├── js/auth.js
│   └── admin/
│       ├── admin-dashboard.html
│       ├── manage-users.html
│       ├── manage-destinations.html
│       ├── manage-hotels.html
│       ├── manage-packages.html
│       ├── manage-bookings.html
│       └── manage-payments.html
│
└── database/
    └── tripzen_setup.sql             # MySQL setup script
```

---

## 🚀 How to Run

### Step 1: Database Setup
1. Open **MySQL Workbench**
2. Run the file: `database/tripzen_setup.sql`
3. This creates the database `tripzen_db` with all tables and sample data

### Step 2: Configure Database (if needed)
Edit `backend/src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/tripzen_db
spring.datasource.username=root
spring.datasource.password=root    ← Change to your MySQL password
```

### Step 3: Run the Backend
```bash
cd backend
mvn spring-boot:run
```
The backend starts at: **http://localhost:8080**

### Step 4: Open the Frontend
Simply open `frontend/index.html` in your browser.

---

## 🔑 Test Credentials

| Role  | Email                  | Password     |
|-------|------------------------|--------------|
| Admin | admin@tripzen.com      | password123  |
| User  | user@tripzen.com       | password123  |

---

## 🌐 REST API Endpoints

### Authentication
```
POST /api/auth/register    → Register new user
POST /api/auth/login       → Login and get JWT token
```

### Destinations (Public)
```
GET    /api/destinations          → Get all
GET    /api/destinations/{id}     → Get by ID
GET    /api/destinations/search?name= → Search
POST   /api/destinations          → Add (Admin only)
PUT    /api/destinations/{id}     → Update (Admin only)
DELETE /api/destinations/{id}     → Delete (Admin only)
```

### Hotels (Public read)
```
GET  /api/hotels                        → All hotels
GET  /api/hotels/{id}                   → Hotel by ID
GET  /api/hotels/destination/{id}       → Hotels by destination
POST /api/hotels                        → Add (Admin)
PUT  /api/hotels/{id}                   → Update (Admin)
DELETE /api/hotels/{id}                 → Delete (Admin)
```

### Packages (Public read)
```
GET  /api/packages                      → All packages
GET  /api/packages/{id}                 → Package detail
GET  /api/packages/search?name=         → Search
POST /api/packages                      → Add (Admin)
PUT  /api/packages/{id}                 → Update (Admin)
DELETE /api/packages/{id}               → Delete (Admin)
```

### Bookings (Authenticated)
```
POST /api/bookings                      → Create booking
GET  /api/bookings/my                   → My bookings
GET  /api/bookings/{id}                 → Booking detail
PUT  /api/bookings/{id}/cancel          → Cancel booking
GET  /api/bookings                      → All bookings (Admin)
PUT  /api/bookings/{id}/status          → Update status (Admin)
```

### Payments
```
POST /api/payments                      → Process payment
GET  /api/payments/{id}                 → Payment by ID
GET  /api/payments/booking/{id}         → Payment by booking
```

### Admin
```
GET  /api/admin/dashboard               → Dashboard stats
```

---

## 🗃️ Database Schema

```sql
users            (id, name, email, password, role, phone, created_at)
destinations     (id, name, country, state, description, image_url, best_time_to_visit)
hotels           (id, name, location, price_per_night, rating, available_rooms, destination_id)
travel_packages  (id, name, description, duration_days, price, inclusions, destination_id)
bookings         (id, user_id, package_id, travel_date, persons, total_price, status)
payments         (id, booking_id, amount, payment_method, payment_status, transaction_id)
```

---

## ✨ Features

### User Features
- ✅ Register & Login with JWT
- ✅ Browse destinations, hotels, packages
- ✅ Search & filter packages
- ✅ View package details with price calculator
- ✅ Book a package
- ✅ Simulated payment (UPI/Card/Net Banking)
- ✅ View booking confirmation with Transaction ID
- ✅ View & cancel bookings

### Admin Features
- ✅ Admin dashboard with statistics
- ✅ Manage users (view, delete)
- ✅ Manage destinations (CRUD)
- ✅ Manage hotels (CRUD)
- ✅ Manage packages (CRUD)
- ✅ View & update booking status
- ✅ View payment records

---

## 🔒 Security

- Passwords encrypted with **BCrypt**
- **JWT tokens** for stateless authentication
- Role-based access control: **USER** and **ADMIN**
- CORS configured for frontend-backend communication

---

*Built with ❤️ as a college project using Java, Spring Boot, MySQL and vanilla JavaScript.*
