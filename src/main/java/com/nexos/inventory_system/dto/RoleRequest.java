package com.nexos.inventory_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RoleRequest {
    @NotBlank(message = "El nombre interno (systemName) es obligatorio")
    @Size(max = 30, message = "El nombre interno no debe superar los 30 caracteres")
    private String systemName;

    @NotBlank(message = "El nombre visible (displayName) es obligatorio")
    @Size(max = 50, message = "El nombre visible no debe superar los 50 caracteres")
    private String displayName;

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
