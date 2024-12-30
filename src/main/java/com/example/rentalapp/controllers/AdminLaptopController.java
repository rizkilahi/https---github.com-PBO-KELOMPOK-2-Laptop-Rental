package com.example.rentalapp.controllers;

import com.example.rentalapp.models.Laptop;
import com.example.rentalapp.services.AdminLaptopService;
import com.example.rentalapp.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminLaptopController {

    private final AdminLaptopService adminLaptopService;
    private final BrandService brandService;

    @Autowired
    public AdminLaptopController(AdminLaptopService adminLaptopService, BrandService brandService) {
        this.adminLaptopService = adminLaptopService;
        this.brandService = brandService;
    }

    @GetMapping("/laptops")
    public String listLaptops(Model model) {
        model.addAttribute("laptops", adminLaptopService.findAll());
        return "admin/laptopList";
    }

    @GetMapping("/laptops/add")
    public String showAddForm(Model model) {
        model.addAttribute("laptop", new Laptop());
        model.addAttribute("brands", brandService.findAll());
        return "admin/laptopAdd";
    }

    @PostMapping("/laptops/add")
    public String addLaptop(@Valid @ModelAttribute Laptop laptop, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("brands", brandService.findAll());
            return "admin/laptopAdd";
        }
        adminLaptopService.save(laptop);
        return "redirect:/admin/laptops?success=added";
    }

    @GetMapping("/laptops/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            Laptop laptop = adminLaptopService.findById(id);
            model.addAttribute("laptop", laptop);
            model.addAttribute("brands", brandService.findAll());
            return "admin/laptopEdit";
        } catch (RuntimeException e) {
            return "redirect:/admin/laptops?error=notfound";
        }
    }

    @PostMapping("/laptops/edit/{id}")
    public String updateLaptop(@PathVariable Long id, @Valid @ModelAttribute Laptop laptop,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("brands", brandService.findAll());
            return "admin/laptopEdit";
        }
        laptop.setId(id);
        adminLaptopService.save(laptop);
        return "redirect:/admin/laptops?success=updated";
    }

    @GetMapping("/laptops/delete/{id}")
    public String deleteLaptop(@PathVariable Long id) {
        try {
            adminLaptopService.delete(id);
            return "redirect:/admin/laptops?success=deleted";
        } catch (RuntimeException e) {
            return "redirect:/admin/laptops?error=notfound";
        }
    }
}
