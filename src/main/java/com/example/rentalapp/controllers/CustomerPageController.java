/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.rentalapp.controllers;

/**
 *
 * @author rizkilahi
 */
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerPageController {
    @GetMapping("/customer/home")
    public String customerHome(Model model) {
        model.addAttribute("title", "Customer Dashboard");
        return "customer/home";
    }
}
