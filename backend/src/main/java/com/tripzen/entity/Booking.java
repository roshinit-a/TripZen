package com.tripzen.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id", nullable = false)
    private TravelPackage travelPackage;

    @Column(name = "travel_date", nullable = false)
    private LocalDate travelDate;

    @Column(nullable = false)
    private Integer persons;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Column(length = 30)
    private String status = "PENDING"; // PENDING, CONFIRMED, CANCELLED

    @Column(name = "booked_at")
    private LocalDateTime bookedAt;

    @Column(name = "special_requests", columnDefinition = "TEXT")
    private String specialRequests;

    @PrePersist
    protected void onCreate() {
        bookedAt = LocalDateTime.now();
    }
}
