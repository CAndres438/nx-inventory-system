package com.nexos.inventory_system.controllers;


import com.nexos.inventory_system.dto.InventoryFilterRequest;
import com.nexos.inventory_system.dto.InventoryRequest;
import com.nexos.inventory_system.dto.InventoryResponse;
import com.nexos.inventory_system.services.IInventoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class InventoryController {
    private final IInventoryService inventoryService;

    public InventoryController(IInventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/inventory")
    public ResponseEntity<InventoryResponse> create(@Valid @RequestBody InventoryRequest request) {
        return ResponseEntity.ok(inventoryService.create(request));
    }

    @PutMapping("inventory/{id}")
    public ResponseEntity<InventoryResponse> update(@PathVariable Long id,
                                                    @Valid @RequestBody InventoryRequest request) {
        return ResponseEntity.ok(inventoryService.update(id, request));
    }

    @DeleteMapping("inventory/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        inventoryService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("inventory/filter")
    public ResponseEntity<List<InventoryResponse>> filter(@RequestBody InventoryFilterRequest filters) {
        return ResponseEntity.ok(inventoryService.filter(filters));
    }
}
