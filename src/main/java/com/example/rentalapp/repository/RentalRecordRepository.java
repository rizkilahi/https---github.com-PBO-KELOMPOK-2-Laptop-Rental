/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.rentalapp.repository;

/**
 *
 * @author rizkilahi
 */
import com.example.rentalapp.models.RentalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalRecordRepository extends JpaRepository<RentalRecord, Long> {
    List<RentalRecord> findByBooking_Id(Long bookingId);
}
