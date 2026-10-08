package com.tripzen.controller;

import com.tripzen.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired private UserService userService;
    @Autowired private TravelPackageService packageService;
    @Autowired private BookingService bookingService;

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userService.getTotalUsers());
        stats.put("totalPackages", packageService.getTotalPackages());
        stats.put("totalBookings", bookingService.getTotalBookings());
        stats.put("totalRevenue", bookingService.getTotalRevenue());
        return ResponseEntity.ok(stats);
    }
}
