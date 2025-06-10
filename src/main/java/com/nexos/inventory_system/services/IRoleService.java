package com.nexos.inventory_system.services;

import com.nexos.inventory_system.dto.RoleRequest;
import com.nexos.inventory_system.dto.RoleResponse;

import java.util.List;

public interface IRoleService {
    List<RoleResponse> getAllRoles();
    List<RoleResponse> getActiveRoles();
    RoleResponse createRole(RoleRequest request);
    RoleResponse getRoleById(Integer id);
    void updateRoleStatus(Integer id, boolean active);
    void updateRole(Integer id, RoleRequest request);
}
