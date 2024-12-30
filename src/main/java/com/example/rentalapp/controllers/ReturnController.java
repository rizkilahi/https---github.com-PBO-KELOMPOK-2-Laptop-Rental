/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.rentalapp.controllers;

/**
 *
 * @author rizkilahi
 */
import com.example.rentalapp.models.RentalRecord;
import com.example.rentalapp.services.RentalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/return")
public class ReturnController {

    private final RentalService rentalService;

    public ReturnController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping("/{rentalRecordId}")
    public ResponseEntity<RentalRecord> processReturn(
            @PathVariable Long rentalRecordId,
            @RequestParam LocalDate returnDate
    ) {
        RentalRecord rentalRecord = rentalService.processReturn(rentalRecordId, returnDate);
        return ResponseEntity.ok(rentalRecord);
    }
}
