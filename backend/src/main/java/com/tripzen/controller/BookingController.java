package com.tripzen.controller;

import com.tripzen.dto.BookingRequest;
import com.tripzen.entity.*;
import com.tripzen.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    @Autowired private BookingService bookingService;
    @Autowired private UserService userService;

    // Create a new booking
    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingRequest request,
                                            Authentication auth) {
        try {
            User user = userService.getUserByEmail(auth.getName());
            Booking booking = bookingService.createBooking(user.getId(), request);
            return ResponseEntity.ok(booking);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // Get logged-in user's bookings
    @GetMapping("/my")
    public ResponseEntity<?> getMyBookings(Authentication auth) {
        try {
            User user = userService.getUserByEmail(auth.getName());
            return ResponseEntity.ok(bookingService.getMyBookings(user.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // Get a specific booking
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(bookingService.getById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Cancel booking
    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancel(@PathVariable Long id, Authentication auth) {
        try {
            User user = userService.getUserByEmail(auth.getName());
            Booking booking = bookingService.cancelBooking(id, user.getId());
            return ResponseEntity.ok(booking);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // Admin: get all bookings
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    // Admin: update booking status
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateStatus(@PathVariable Long id,
                                           @RequestParam String status) {
        try {
            return ResponseEntity.ok(bookingService.updateStatus(id, status));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
