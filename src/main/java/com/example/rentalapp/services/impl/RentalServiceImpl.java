/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.rentalapp.services.impl;

/**
 *
 * @author rizkilahi
 */
import com.example.rentalapp.models.*;
import com.example.rentalapp.repository.*;
import com.example.rentalapp.services.RentalService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class RentalServiceImpl implements RentalService {

    private static final BigDecimal DAILY_FINE = BigDecimal.valueOf(50000); // Denda Rp50.000 per hari

    private final LaptopRepository laptopRepository;
    private final BookingRepository bookingRepository;
    private final RentalRecordRepository rentalRecordRepository;
    private final UserRepository userRepository;

    public RentalServiceImpl(
            LaptopRepository laptopRepository,
            BookingRepository bookingRepository,
            RentalRecordRepository rentalRecordRepository,
            UserRepository userRepository
    ) {
        this.laptopRepository = laptopRepository;
        this.bookingRepository = bookingRepository;
        this.rentalRecordRepository = rentalRecordRepository;
        this.userRepository = userRepository;
    }

    @Override
    public BigDecimal calculateFine(LocalDate returnDate, LocalDate dueDate) {
        long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
        return daysLate > 0 ? DAILY_FINE.multiply(BigDecimal.valueOf(daysLate)) : BigDecimal.ZERO;
    }

    @Override
    public RentalRecord processReturn(Long rentalRecordId, LocalDate returnDate) {
        // Validasi Rental Record
        RentalRecord rentalRecord = rentalRecordRepository.findById(rentalRecordId)
                .orElseThrow(() -> new RuntimeException("Rental record not found with ID: " + rentalRecordId));

        // Hitung Denda
        LocalDate dueDate = rentalRecord.getRentalEndDate();
        BigDecimal fine = calculateFine(returnDate, dueDate);

        // Perbarui Rental Record
        rentalRecord.setReturnDate(returnDate);
        rentalRecord.setTotalFee(rentalRecord.getTotalFee().add(fine));

        // Perbarui Laptop
        Laptop laptop = rentalRecord.getLaptop();
        laptop.setStatus(Laptop.LaptopStatus.AVAILABLE);
        laptopRepository.save(laptop);

        // Simpan perubahan
        rentalRecordRepository.save(rentalRecord);

        return rentalRecord;
    }

    @Override
    public Booking createBooking(Long customerId, List<Long> laptopIds, LocalDate rentalStartDate, LocalDate rentalEndDate) {
        // Tetap sama dengan implementasi sebelumnya
        return null;
    }
}
