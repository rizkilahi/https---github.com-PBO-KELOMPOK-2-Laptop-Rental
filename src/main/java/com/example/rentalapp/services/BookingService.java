/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.rentalapp.services;

/**
 *
 * @author rizkilahi
 */
import com.example.rentalapp.dto.BookingRequestDto;
import com.example.rentalapp.models.Booking;
import com.example.rentalapp.models.Laptop;
import com.example.rentalapp.models.Laptop.LaptopStatus;
import com.example.rentalapp.models.RentalRecord;
import com.example.rentalapp.models.User;
import com.example.rentalapp.repository.BookingRepository;
import com.example.rentalapp.repository.LaptopRepository;
import com.example.rentalapp.repository.RentalRecordRepository;
import com.example.rentalapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final LaptopRepository laptopRepository;
    private final UserRepository userRepository;
    private final RentalRecordRepository rentalRecordRepository;

    public BookingService(BookingRepository bookingRepository, LaptopRepository laptopRepository,
            UserRepository userRepository, RentalRecordRepository rentalRecordRepository) {
        this.bookingRepository = bookingRepository;
        this.laptopRepository = laptopRepository;
        this.userRepository = userRepository;
        this.rentalRecordRepository = rentalRecordRepository;
    }

    public Booking createBooking(BookingRequestDto bookingRequestDto, Long userId) {
        // Cari user berdasarkan ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        // Validasi laptop berdasarkan ID
        Laptop laptop = laptopRepository.findById(bookingRequestDto.getLaptopId())
                .orElseThrow(() -> new IllegalArgumentException("Laptop not found."));

        // Pastikan laptop tersedia
        if (!laptop.getStatus().equals(LaptopStatus.AVAILABLE)) {
            throw new IllegalStateException("Laptop is not available for booking.");
        }

        // Validasi tanggal
        if (bookingRequestDto.getRentalStartDate().isAfter(bookingRequestDto.getRentalEndDate())) {
            throw new IllegalArgumentException("Rental start date cannot be after rental end date.");
        }

        // Hitung total harga
        long rentalDays = bookingRequestDto.getRentalStartDate()
                .until(bookingRequestDto.getRentalEndDate())
                .getDays();
        BigDecimal totalPrice = BigDecimal.valueOf(rentalDays)
                .multiply(laptop.getRentalPricePerDay());

        // Buat objek Booking baru
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus(Booking.BookingStatus.PENDING);
        booking.setTotalPrice(totalPrice);

        // Simpan Booking ke database
        Booking savedBooking = bookingRepository.save(booking);

        // Buat RentalRecord untuk laptop
        RentalRecord rentalRecord = new RentalRecord();
        rentalRecord.setBooking(savedBooking);
        rentalRecord.setLaptop(laptop);
        rentalRecord.setRentalStartDate(bookingRequestDto.getRentalStartDate());
        rentalRecord.setRentalEndDate(bookingRequestDto.getRentalEndDate());
        rentalRecord.setTotalFee(totalPrice);

        // Simpan RentalRecord ke database
        rentalRecordRepository.save(rentalRecord);

        // Update status laptop menjadi RENTED
        laptop.setStatus(Laptop.LaptopStatus.RENTED);
        laptopRepository.save(laptop);

        return savedBooking;
    }

    public Optional<Booking> findById(Long bookingId) {
        return bookingRepository.findById(bookingId);
    }

    public Booking cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found."));
        booking.setStatus(Booking.BookingStatus.CANCELLED);
        return bookingRepository.save(booking);
    }

    public Booking completeBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found."));
        booking.setStatus(Booking.BookingStatus.COMPLETED);
        return bookingRepository.save(booking);
    }
}
