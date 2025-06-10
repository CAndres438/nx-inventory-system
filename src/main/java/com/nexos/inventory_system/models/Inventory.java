package com.nexos.inventory_system.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "inventory", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private LocalDate dateIn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modified_by")
    private User modifiedBy;

    private LocalDate dateModified;

    public Inventory() {}

    public Inventory(Long id, String name, Integer quantity, LocalDate dateIn,
                     User createdBy, User modifiedBy, LocalDate dateModified) {
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

    public User getCreatedBy() { return createdBy; }
    public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }

    public User getModifiedBy() { return modifiedBy; }
    public void setModifiedBy(User modifiedBy) { this.modifiedBy = modifiedBy; }

    public LocalDate getDateModified() { return dateModified; }
    public void setDateModified(LocalDate dateModified) { this.dateModified = dateModified; }
}
