package com.nexos.inventory_system.controllers;

import com.nexos.inventory_system.dto.RoleRequest;
import com.nexos.inventory_system.dto.RoleResponse;
import com.nexos.inventory_system.services.IRoleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
public class RoleController {

    private final IRoleService roleService;

    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @GetMapping("/roles/active")
    public ResponseEntity<List<RoleResponse>> getActiveRoles() {
        return ResponseEntity.ok(roleService.getActiveRoles());
    }

    @PostMapping("/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoleResponse> createRole(@Valid @RequestBody RoleRequest request) {
        return ResponseEntity.ok(roleService.createRole(request));
    }

    @GetMapping("/roles/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable Integer id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @PutMapping("/roles/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateRole(@PathVariable Integer id, @Valid @RequestBody RoleRequest request) {
        roleService.updateRole(id, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/roles/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateRoleStatus(@PathVariable Integer id, @RequestBody Map<String, Boolean> body) {
        Boolean active = body.get("active");
        roleService.updateRoleStatus(id, active);
        return ResponseEntity.ok().build();
    }
}
