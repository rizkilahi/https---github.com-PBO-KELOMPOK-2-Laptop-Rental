package com.example.rentalapp.controllers;

import com.example.rentalapp.models.User;
import com.example.rentalapp.models.User.Role;
import com.example.rentalapp.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid User user, Model model) {
        // Validasi username tidak boleh kosong atau null
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            model.addAttribute("error", "Username is required!");
            return "register";
        }

        // Validasi email tidak boleh kosong atau null
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            model.addAttribute("error", "Email is required!");
            return "register";
        }

        // Periksa apakah username sudah ada
        if (userRepository.existsByUsername(user.getUsername())) {
            model.addAttribute("error", "Username already exists!");
            return "register";
        }

        // Periksa apakah email sudah ada
        if (userRepository.existsByEmail(user.getEmail())) {
            model.addAttribute("error", "Email already exists!");
            return "register";
        }

        // Enkripsi password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Tetapkan role default ke ROLE_CUSTOMER
        if (user.getRole() == null) {
            user.setRole(Role.ROLE_CUSTOMER);
        }

        // Simpan user ke database
        userRepository.save(user);

        // Redirect ke halaman login setelah registrasi berhasil
        return "redirect:/login";
    }

    @GetMapping("/default")
    public String defaultAfterLogin(Authentication authentication) {
        // Periksa apakah user memiliki role ROLE_ADMIN
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return "redirect:/admin/home";
        } // Periksa apakah user memiliki role ROLE_CUSTOMER
        else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {
            return "redirect:/customer/home";
        }
        // Jika tidak ada role, anggap sebagai CUSTOMER secara default
        return "redirect:/customer/home";
    }
}
