package com.nexos.inventory_system.dto;

public class AuthResponse {
    private String token;
   /* private String username;
    private String name;
    private String role;*/

    public AuthResponse() {
    }

    public AuthResponse(String token) {
        this.token = token;
       /* this.username = username;
        this.name = name;
        this.role = role;*/
    }
    public String getToken() { return token; }
    /*public String getUsername() { return username; }
    public String getName() { return name; }
    public String getRole() { return role; }*/
}
