/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.rentalapp.services;

import com.example.rentalapp.models.Booking;
import com.example.rentalapp.models.RentalRecord;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author rizkilahi
 */
import java.math.BigDecimal;

public interface RentalService {
    Booking createBooking(Long customerId, List<Long> laptopIds, LocalDate rentalStartDate, LocalDate rentalEndDate);

    RentalRecord processReturn(Long rentalRecordId, LocalDate returnDate);

    BigDecimal calculateFine(LocalDate returnDate, LocalDate dueDate);
}
