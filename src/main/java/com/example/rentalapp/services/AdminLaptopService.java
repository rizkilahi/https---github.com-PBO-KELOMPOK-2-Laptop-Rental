/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.rentalapp.services;

/**
 *
 * @author rizkilahi
 */
import com.example.rentalapp.models.Laptop;
import com.example.rentalapp.models.Laptop.LaptopStatus;
import com.example.rentalapp.models.Brand;
import com.example.rentalapp.repository.LaptopRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class AdminLaptopService {

    @Autowired
    private LaptopRepository laptopRepository;

    public List<Laptop> findAll() {
        return laptopRepository.findAll();
    }

    public List<Laptop> findAllAvailableLaptops() {
        return laptopRepository.findByStatus(Laptop.LaptopStatus.AVAILABLE);
    }
    
    public Laptop findById(Long id) {
        return laptopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Laptop not found with id: " + id));
    }

    @Transactional
    public Laptop save(Laptop laptop) {
        return laptopRepository.save(laptop);
    }

    @Transactional
    public void delete(Long id) {
        laptopRepository.deleteById(id);
    }

    @Transactional
    public void updateLaptopAvailability(Long laptopId, LaptopStatus status) {
        Laptop laptop = findById(laptopId);
        laptop.setStatus(status);
        laptopRepository.save(laptop);
    }
}
