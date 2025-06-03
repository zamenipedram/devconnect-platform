//package com.devconnects.authservice.controller;
//
//
//import com.devconnects.authservice.dto.RegisterRequest;
//import com.devconnects.authservice.model.UserEntity;
//import com.devconnects.authservice.repository.UserRepository;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/auth")
//@RequiredArgsConstructor
//public class RegisterController {
//
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @PostMapping("/register")
//    public String registerUser(@Valid @RequestBody RegisterRequest request) {
//        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
//            throw new RuntimeException("Username already taken");
//        }
//
//        UserEntity user = UserEntity.builder()
//                .username(request.getUsername())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .role("USER") // Default role
//                .build();
//
//        userRepository.save(user);
//        return "User registered successfully!";
//    }
//}
