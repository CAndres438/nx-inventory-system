package com.nexos.inventory_system.config;

import com.nexos.inventory_system.models.Role;
import com.nexos.inventory_system.models.User;
import com.nexos.inventory_system.repositories.RoleRepository;
import com.nexos.inventory_system.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DatabaseInitializer {
    private static final Logger log = LoggerFactory.getLogger(DatabaseInitializer.class);

    private final RoleRepository roleRepo;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public DatabaseInitializer(RoleRepository roleRepo, UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        createRoleIfNotExists("ROLE_ADMIN", "Administrador");
        createRoleIfNotExists("ROLE_USER", "Asesor de ventas");
        createRoleIfNotExists("ROLE_SUPPORT", "Soporte");

        createUserIfNotExists("admin1", "Andres O Admin", "admin123", "ROLE_ADMIN", 27);
        createUserIfNotExists("user1", "Andres O Ventas", "user123", "ROLE_USER", 27);
        createUserIfNotExists("support1", "Andres O Soporte", "soporte123", "ROLE_SUPPORT", 27);

    }

    private void createRoleIfNotExists(String name, String displayName) {
        roleRepo.findByName(name).orElseGet(() -> {
            log.info("✅ Creating role: {}", displayName);
            return roleRepo.save(new Role(null, name, displayName));
        });
    }

    private void createUserIfNotExists(String username, String name, String rawPassword, String roleName, Integer age) {
        if (userRepo.findByUsername(username).isEmpty()) {
            Role role = roleRepo.findByName(roleName).orElseThrow();
            User user = new User();
            user.setName(name);
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(rawPassword));
            user.setAge(age);
            user.setRoles(Set.of(role));
            userRepo.save(user);
            log.info("✅ User created: {} / {}", username, rawPassword);
        }
    }
}
