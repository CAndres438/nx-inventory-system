package com.nexos.inventory_system.controllers;

import com.nexos.inventory_system.dto.AuthLoginRequest;
import com.nexos.inventory_system.dto.AuthRegisterRequest;
import com.nexos.inventory_system.dto.AuthResponse;
import com.nexos.inventory_system.services.IAuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {
    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid AuthRegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
}
