package com.nexos.inventory_system.services;

import com.nexos.inventory_system.dto.RoleRequest;
import com.nexos.inventory_system.dto.RoleResponse;
import com.nexos.inventory_system.models.Role;
import com.nexos.inventory_system.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(role -> new RoleResponse(role.getId(), role.getDisplayName(), role.getActive()))
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleResponse> getActiveRoles() {
        return roleRepository.findAll()
                .stream()
                .filter(Role::getActive)
                .map(role -> new RoleResponse(role.getId(), role.getDisplayName(), true))
                .collect(Collectors.toList());
    }

    @Override
    public RoleResponse createRole(RoleRequest request) {
        String internalName = "ROLE_" + request.getSystemName().toUpperCase();

        if (roleRepository.findByName(internalName).isPresent()) {
            throw new IllegalArgumentException("Ya existe un rol con ese nombre interno.");
        }

        Role role = new Role();
        role.setName(internalName);
        role.setDisplayName(request.getDisplayName());
        role.setActive(true);

        Role saved = roleRepository.save(role);
        return new RoleResponse(saved.getId(), saved.getDisplayName(), saved.getActive());
    }

    @Override
    public RoleResponse getRoleById(Integer id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El rol no existe con ID: " + id));

        return new RoleResponse(role.getId(), role.getDisplayName(), role.getActive());
    }

    @Override
    public void updateRoleStatus(Integer id, boolean active) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));
        role.setActive(active);
        roleRepository.save(role);
    }

    @Override
    public void updateRole(Integer id, RoleRequest request) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));
        role.setDisplayName(request.getDisplayName());
        roleRepository.save(role);
    }
}
