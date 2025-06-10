package com.nexos.inventory_system.dto;

import java.time.LocalDate;

public class InventoryResponse {
    private Long id;
    private String name;
    private Integer quantity;
    private LocalDate dateIn;
    private String createdBy;
    private String modifiedBy;
    private LocalDate dateModified;

    public InventoryResponse() {}

    public InventoryResponse(Long id, String name, Integer quantity, LocalDate dateIn,
                             String createdBy, String modifiedBy, LocalDate dateModified) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.dateIn = dateIn;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
        this.dateModified = dateModified;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public LocalDate getDateIn() { return dateIn; }
    public void setDateIn(LocalDate dateIn) { this.dateIn = dateIn; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public String getModifiedBy() { return modifiedBy; }
    public void setModifiedBy(String modifiedBy) { this.modifiedBy = modifiedBy; }

    public LocalDate getDateModified() { return dateModified; }
    public void setDateModified(LocalDate dateModified) { this.dateModified = dateModified; }

}
