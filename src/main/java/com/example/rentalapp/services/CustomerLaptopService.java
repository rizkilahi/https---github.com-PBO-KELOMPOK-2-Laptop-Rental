/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.rentalapp.services;

/**
 *
 * @author rizkilahi
 */
import com.example.rentalapp.models.Booking;
import com.example.rentalapp.models.Laptop;
import com.example.rentalapp.models.Laptop.LaptopStatus;
import com.example.rentalapp.models.Brand;
import com.example.rentalapp.repository.BookingRepository;
import com.example.rentalapp.repository.LaptopRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CustomerLaptopService {

    @Autowired
    private LaptopRepository laptopRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public List<Laptop> findAllAvailableLaptops() {
        return laptopRepository.findByStatus(Laptop.LaptopStatus.AVAILABLE);
    }

    public Laptop findLaptopById(Long id) {
        return laptopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Laptop not found with id: " + id));
    }

    public void bookLaptop(Long laptopId, Long userId, String startDate, String endDate) {
        Laptop laptop = findLaptopById(laptopId);

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date must be before end date.");
        }

        // Hitung total harga berdasarkan durasi
        long days = java.time.temporal.ChronoUnit.DAYS.between(start, end);
        BigDecimal totalPrice = laptop.getRentalPricePerDay().multiply(BigDecimal.valueOf(days));

        // Buat booking baru
        Booking booking = new Booking();
        booking.setLaptop(laptop);
        booking.setStartDate(start);
        booking.setEndDate(end);
        booking.setStatus(Booking.BookingStatus.PENDING);
        booking.setTotalPrice(totalPrice);
        booking.setBookingDate(LocalDateTime.now());

        // Simpan booking
        bookingRepository.save(booking);

        // Update status laptop
        laptop.setStatus(Laptop.LaptopStatus.RENTED);
        laptopRepository.save(laptop);
    }

    @Transactional
    public void updateLaptopAvailability(Long laptopId, Laptop.LaptopStatus status) {
        Laptop laptop = findLaptopById(laptopId);
        laptop.setStatus(status);
        laptopRepository.save(laptop);
    }
}
