package com.nexos.inventory_system.repositories;

import com.nexos.inventory_system.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);

    List<Inventory> findByNameContainingIgnoreCaseAndDateInBetweenAndCreatedBy_Name(
            String name, LocalDate start, LocalDate end, String createdBy);
}
