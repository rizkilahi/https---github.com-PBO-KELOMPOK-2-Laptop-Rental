/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.rentalapp.services.impl;

/**
 *
 * @author rizkilahi
 */
import com.example.rentalapp.models.RentalRecord;
import com.example.rentalapp.repository.RentalRecordRepository;
import com.example.rentalapp.services.ReturnService;
import org.springframework.stereotype.Service;

@Service
public class ReturnServiceImpl implements ReturnService {

    private final RentalRecordRepository rentalRecordRepository;

    public ReturnServiceImpl(RentalRecordRepository rentalRecordRepository) {
        this.rentalRecordRepository = rentalRecordRepository;
    }

    @Override
    public RentalRecord processReturn(RentalRecord rentalRecord) {
        // Implementasikan logika penghitungan denda, jika diperlukan
        return rentalRecordRepository.save(rentalRecord);
    }
}
