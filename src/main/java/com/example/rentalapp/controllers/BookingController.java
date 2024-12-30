/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.rentalapp.controllers;

/**
 *
 * @author rizkilahi
 */
import com.example.rentalapp.dto.BookingRequestDto;
import com.example.rentalapp.models.Booking;
import com.example.rentalapp.models.RentalRecord;
import com.example.rentalapp.services.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // Create a new booking
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody BookingRequestDto bookingRequestDto, @RequestParam Long userId) {
        Booking booking = bookingService.createBooking(bookingRequestDto, userId);
        return ResponseEntity.ok(booking);
    }

    // Get all bookings by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        return bookingService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Cancel a booking
    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<Booking> cancelBooking(@PathVariable Long bookingId) {
        Booking booking = bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok(booking);
    }

    // Complete a booking
    @PutMapping("/{bookingId}/complete")
    public ResponseEntity<Booking> completeBooking(@PathVariable Long bookingId) {
        Booking booking = bookingService.completeBooking(bookingId);
        return ResponseEntity.ok(booking);
    }
}
