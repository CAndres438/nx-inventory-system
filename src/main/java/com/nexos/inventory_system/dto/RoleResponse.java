package com.nexos.inventory_system.dto;

public class RoleResponse {
    private Integer id;
    private String displayName;
    private Boolean active;

    public RoleResponse(Integer id, String displayName, Boolean active) {
        this.id = id;
        this.displayName = displayName;
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Boolean getActive() { return active;}
}
