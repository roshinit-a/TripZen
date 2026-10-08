package com.tripzen.service;

import com.tripzen.dto.BookingRequest;
import com.tripzen.entity.*;
import com.tripzen.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired private BookingRepository bookingRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private TravelPackageRepository packageRepository;

    public Booking createBooking(Long userId, BookingRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        TravelPackage pkg = packageRepository.findById(request.getPackageId())
                .orElseThrow(() -> new RuntimeException("Package not found"));

        // Backend calculates total price — never trust frontend!
        double totalPrice = pkg.getPrice() * request.getPersons();

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setTravelPackage(pkg);
        booking.setTravelDate(request.getTravelDate());
        booking.setPersons(request.getPersons());
        booking.setTotalPrice(totalPrice);
        booking.setStatus("PENDING");
        booking.setSpecialRequests(request.getSpecialRequests());

        return bookingRepository.save(booking);
    }

    public List<Booking> getMyBookings(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public Booking getById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found: " + id));
    }

    public Booking cancelBooking(Long bookingId, Long userId) {
        Booking booking = getById(bookingId);
        if (!booking.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized: This is not your booking");
        }
        if ("CONFIRMED".equals(booking.getStatus())) {
            throw new RuntimeException("Cannot cancel a confirmed booking");
        }
        booking.setStatus("CANCELLED");
        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking updateStatus(Long id, String status) {
        Booking booking = getById(id);
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    public Double getTotalRevenue() {
        Double rev = bookingRepository.getTotalRevenue();
        return rev != null ? rev : 0.0;
    }

    public Long getTotalBookings() {
        return bookingRepository.getTotalBookings();
    }
}
