/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.rentalapp.controllers;

import com.example.rentalapp.models.Brand;
import com.example.rentalapp.models.Laptop;
import com.example.rentalapp.services.AdminLaptopService;
import com.example.rentalapp.services.BrandService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author rizkilahi
 */
@Controller
@RequestMapping("/admin/laptops")
public class BrandController {
    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/brands")
    public String listBrand(Model model) {
        model.addAttribute("brands", brandService.findAll());
        return "admin/brands";
    }

    @GetMapping("/brands/add")
    public String showAddForm(Model model) {
        model.addAttribute("brand", new Brand());
        model.addAttribute("brands", brandService.findAll());
        return "admin/brandAdd";
    }

    @PostMapping("/brands/add")
    public String addLaptop(@Valid @ModelAttribute Brand brand, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("brands", brandService.findAll());
            return "admin/brandAdd";
        }
        brandService.save(brand);
        return "redirect:/admin/brands?success=added";
    }
}
