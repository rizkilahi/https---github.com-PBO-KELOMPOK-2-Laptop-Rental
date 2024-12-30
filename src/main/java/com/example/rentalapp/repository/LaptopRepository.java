/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.rentalapp.repository;

/**
 *
 * @author rizkilahi
 */
import com.example.rentalapp.models.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaptopRepository extends JpaRepository<Laptop, Long> {
    // Cari laptop berdasarkan status
    List<Laptop> findByStatus(Laptop.LaptopStatus status);

    public Object findLaptopById(Long id);
}
