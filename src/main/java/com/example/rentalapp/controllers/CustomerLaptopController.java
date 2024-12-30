/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.rentalapp.controllers;

/**
 *
 * @author rizkilahi
 */
import com.example.rentalapp.dto.BookingRequestDto;
import com.example.rentalapp.models.Laptop;
import com.example.rentalapp.models.User;
import com.example.rentalapp.services.BookingService;
import com.example.rentalapp.services.CustomerLaptopService;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/customer")
public class CustomerLaptopController {

    @Autowired
    private CustomerLaptopService customerLaptopService;

    @GetMapping("/laptops")
    public String listAvailableLaptops(Model model) {
        List<Laptop> availableLaptops = customerLaptopService.findAllAvailableLaptops();
        model.addAttribute("laptops", availableLaptops);
        return "customer/laptopList"; // Template HTML untuk daftar laptop
    }

    @GetMapping("/bookings/{id}")
    public String initiateBooking(@PathVariable("id") Long laptopId) {
        return "redirect:/customer/bookingForm?laptopId=" + laptopId; // Redirect dengan query parameter
    }

    @GetMapping("/bookingForm")
    public String showBookingForm(Model model, @RequestParam("laptopId") Long laptopId) {
        try {
            Laptop laptop = customerLaptopService.findLaptopById(laptopId);
            model.addAttribute("laptop", laptop);
            return "customer/bookingForm"; // Template HTML untuk form booking
        } catch (RuntimeException e) {
            model.addAttribute("error", "Laptop not found!");
            return "customer/error"; // Template HTML untuk menampilkan error
        }
    }

    @Autowired
    private BookingService bookingService;

    @PostMapping("/bookings")
    public String confirmBooking(@RequestParam("laptopId") Long laptopId,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            Model model) {
        try {
            // Ambil userId dari pengguna yang sedang login
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            Long userId = user.getId();

            // Buat DTO untuk permintaan booking
            BookingRequestDto bookingRequestDto = new BookingRequestDto();
            bookingRequestDto.setLaptopId(laptopId);
            bookingRequestDto.setRentalStartDate(LocalDate.parse(startDate));
            bookingRequestDto.setRentalEndDate(LocalDate.parse(endDate));

            // Gunakan BookingService untuk memproses booking
            bookingService.createBooking(bookingRequestDto, userId);

            return "redirect:/customer/laptops";
        } catch (RuntimeException e) {
            model.addAttribute("error", "Booking failed: " + e.getMessage());
            return "customer/error";
        }
    }
}
