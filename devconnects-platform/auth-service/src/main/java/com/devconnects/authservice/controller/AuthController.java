package com.devconnects.authservice.controller;


import com.devconnects.authservice.config.JwtUtils;
import com.devconnects.authservice.dto.AuthenticationRequest;
import com.devconnects.authservice.dto.AuthenticationResponse;
import com.devconnects.authservice.service.TokenBlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final TokenBlacklistService tokenBlacklistService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public AuthenticationResponse authenticateUser(@RequestBody AuthenticationRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            String token = jwtUtils.generateAccessToken(authRequest.getUsername());
            return new AuthenticationResponse(token);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid login credentials");
        }
    }


    @PostMapping("/logout")
    public String logout(@RequestParam String accessToken) {
        tokenBlacklistService.blacklistToken(accessToken);
        return "Logged out successfully";
    }

    @PostMapping("/refresh")
    public AuthenticationResponse refreshToken(@RequestParam String refreshToken) {
        if (tokenBlacklistService.isTokenBlacklisted(refreshToken)) {
            throw new RuntimeException("Refresh token is blacklisted");
        }

        String username = jwtUtils.getUsernameFromToken(refreshToken);
        String newAccessToken = jwtUtils.generateAccessToken(username);
        return new AuthenticationResponse(newAccessToken);
    }


}
