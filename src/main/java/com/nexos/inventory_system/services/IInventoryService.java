package com.nexos.inventory_system.services;

import com.nexos.inventory_system.dto.InventoryFilterRequest;
import com.nexos.inventory_system.dto.InventoryRequest;
import com.nexos.inventory_system.dto.InventoryResponse;

import java.util.List;

public interface IInventoryService {
    InventoryResponse create(InventoryRequest request);
    InventoryResponse update(Long id, InventoryRequest request);
    void delete(Long id);
    List<InventoryResponse> filter(InventoryFilterRequest filters);
}
