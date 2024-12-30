package com.example.rentalapp.services;

import com.example.rentalapp.models.User;
import com.example.rentalapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Tetapkan role default jika null
        if (user.getRole() == null) {
            user.setRole(User.Role.ROLE_CUSTOMER); // Pastikan Role adalah enum dengan ROLE_CUSTOMER
            userRepository.save(user); // Simpan perubahan ke database
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRole().name()) // Gunakan role sebagai authority
                .build();
    }
}
