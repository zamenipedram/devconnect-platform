package com.devconnects.userservice.controller;

import com.devconnects.commonlib.UserRegisteredEvent;
import com.devconnects.userservice.dto.RegisterRequest;
import com.devconnects.userservice.model.UserEntity;
import com.devconnects.userservice.repository.UserRepository;
import com.devconnects.userservice.service.UserEventPublisher;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class RegisterController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserEventPublisher userEventPublisher;

    @PostMapping("/register")
    public String registerUser(@Valid @RequestBody RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already taken");
        }

        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER") // Default role
                .build();

        userEventPublisher.publishUserCreated(new UserRegisteredEvent(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                "ROLE_USER"
        ));

        userRepository.save(user);
        return "User registered successfully!";
    }
}
