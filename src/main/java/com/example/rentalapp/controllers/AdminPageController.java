package com.example.rentalapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminPageController {

    @GetMapping("/home")
    public String adminHome(Model model) {
        model.addAttribute("title", "Admin Dashboard");
        return "admin/home";
    }
}
