package com.nexos.inventory_system.services;

import com.nexos.inventory_system.dto.AuthLoginRequest;
import com.nexos.inventory_system.dto.AuthRegisterRequest;
import com.nexos.inventory_system.dto.AuthResponse;

public interface IAuthService {
    AuthResponse login(AuthLoginRequest request);
    AuthResponse register(AuthRegisterRequest request);
}
