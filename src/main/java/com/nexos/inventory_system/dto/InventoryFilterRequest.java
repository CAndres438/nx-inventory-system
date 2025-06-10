package com.nexos.inventory_system.dto;

import java.time.LocalDate;

public class InventoryFilterRequest {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String createdBy;

    public InventoryFilterRequest() {}

    public InventoryFilterRequest(String name, LocalDate startDate, LocalDate endDate, String createdBy) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdBy = createdBy;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

}
