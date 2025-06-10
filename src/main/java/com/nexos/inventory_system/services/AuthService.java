package com.nexos.inventory_system.services;

import com.nexos.inventory_system.dto.AuthLoginRequest;
import com.nexos.inventory_system.dto.AuthRegisterRequest;
import com.nexos.inventory_system.dto.AuthResponse;
import com.nexos.inventory_system.models.Role;
import com.nexos.inventory_system.models.User;
import com.nexos.inventory_system.repositories.RoleRepository;
import com.nexos.inventory_system.repositories.UserRepository;
import com.nexos.inventory_system.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService implements IAuthService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepo,
                       RoleRepository roleRepo,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthResponse login(AuthLoginRequest request) {
        User user = userRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        String token = jwtUtil.generateToken(user);


        return new AuthResponse(token);
    }

    @Override
    public AuthResponse register(AuthRegisterRequest request) {
        if (userRepo.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya está registrado");
        }
        if (request.getRoleId() == null) {
            throw new RuntimeException("Debe seleccionar un rol");
        }

        Role role = roleRepo.findById(request.getRoleId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        User newUser = new User();
        newUser.setName(request.getName());
        newUser.setUsername(request.getUsername());
        newUser.setAge(request.getAge());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRoles(Set.of(role));

        userRepo.save(newUser);

        String token = jwtUtil.generateToken(newUser);

        return new AuthResponse(token);
    }
}
