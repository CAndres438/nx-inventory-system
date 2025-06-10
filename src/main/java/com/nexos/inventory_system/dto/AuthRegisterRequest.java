package com.nexos.inventory_system.dto;

import jakarta.validation.constraints.*;

public class AuthRegisterRequest {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(min = 4, max = 10, message = "El usuario debe tener entre 4 y 10 caracteres")
    private String username;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    @NotNull(message = "La edad es obligatoria")
    @Min(value = 18, message = "Debes tener al menos 18 años")
    private Integer age;

    @NotNull(message = "Debes seleccionar un rol")
    private Integer roleId;

    public AuthRegisterRequest() {}

    public AuthRegisterRequest(String name, String username, String password, Integer age, Integer roleId) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.age = age;
        this.roleId = roleId;
    }

    // Getters y setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public Integer getRoleId() { return roleId; }
    public void setRoleId(Integer roleId) { this.roleId = roleId; }
}
