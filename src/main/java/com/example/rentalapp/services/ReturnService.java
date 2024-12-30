/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.rentalapp.services;

/**
 *
 * @author rizkilahi
 */
import com.example.rentalapp.models.RentalRecord;

public interface ReturnService {
    RentalRecord processReturn(RentalRecord rentalRecord);
}
