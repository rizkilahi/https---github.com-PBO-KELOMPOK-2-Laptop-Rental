/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.rentalapp.controllers;

/**
 *
 * @author rizkilahi
 */
import java.time.LocalDate;
import java.util.List;

public class BookingRequest {
    private Long userId;
    private List<Long> laptopIds;
    private LocalDate rentalStartDate;
    private LocalDate rentalEndDate;

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getLaptopIds() {
        return laptopIds;
    }

    public void setLaptopIds(List<Long> laptopIds) {
        this.laptopIds = laptopIds;
    }

    public LocalDate getRentalStartDate() {
        return rentalStartDate;
    }

    public void setRentalStartDate(LocalDate rentalStartDate) {
        this.rentalStartDate = rentalStartDate;
    }

    public LocalDate getRentalEndDate() {
        return rentalEndDate;
    }

    public void setRentalEndDate(LocalDate rentalEndDate) {
        this.rentalEndDate = rentalEndDate;
    }
}
