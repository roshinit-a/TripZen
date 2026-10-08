package com.tripzen.controller;

import com.tripzen.dto.PaymentRequest;
import com.tripzen.entity.Payment;
import com.tripzen.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired private PaymentService paymentService;

    // Process payment
    @PostMapping
    public ResponseEntity<?> processPayment(@RequestBody PaymentRequest request) {
        try {
            Payment payment = paymentService.processPayment(request);
            return ResponseEntity.ok(payment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // Get payment by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(paymentService.getById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get payment by booking
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<?> getByBooking(@PathVariable Long bookingId) {
        try {
            return ResponseEntity.ok(paymentService.getByBookingId(bookingId));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
