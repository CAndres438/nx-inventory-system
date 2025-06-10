package com.nexos.inventory_system.services;

import com.nexos.inventory_system.dto.InventoryFilterRequest;
import com.nexos.inventory_system.dto.InventoryRequest;
import com.nexos.inventory_system.dto.InventoryResponse;
import com.nexos.inventory_system.models.Inventory;
import com.nexos.inventory_system.models.User;
import com.nexos.inventory_system.repositories.InventoryRepository;
import com.nexos.inventory_system.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class InventoryService implements IInventoryService{
    private final InventoryRepository inventoryRepo;
    private final UserRepository userRepo;

    public InventoryService(InventoryRepository inventoryRepo, UserRepository userRepo) {
        this.inventoryRepo = inventoryRepo;
        this.userRepo = userRepo;
    }

    @Override
    public InventoryResponse create(InventoryRequest request) {
        if (inventoryRepo.existsByName(request.getName()))
            throw new IllegalArgumentException("Ya existe un producto con ese nombre");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = ((User) auth.getPrincipal()).getUsername();

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Inventory inventory = new Inventory();
        inventory.setName(request.getName());
        inventory.setQuantity(request.getQuantity());
        inventory.setDateIn(request.getDateIn());
        inventory.setCreatedBy(user);

        inventory = inventoryRepo.save(inventory);
        return toResponse(inventory);
    }

    @Override
    public InventoryResponse update(Long id, InventoryRequest request) {

        Inventory inventory = inventoryRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inventario no encontrado"));

        if (inventoryRepo.existsByNameAndIdNot(request.getName(), id))
            throw new IllegalArgumentException("Ya existe un producto con ese nombre");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = ((User) auth.getPrincipal()).getUsername();

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        inventory.setName(request.getName());
        inventory.setQuantity(request.getQuantity());
        inventory.setDateIn(request.getDateIn());
        inventory.setModifiedBy(user);
        inventory.setDateModified(java.time.LocalDate.now());

        return toResponse(inventoryRepo.save(inventory));
    }

    @Override
    public void delete(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = ((User) auth.getPrincipal()).getUsername();

        Inventory inventory = inventoryRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inventario no encontrado"));

        if (!inventory.getCreatedBy().getUsername().equals(username))
            throw new SecurityException("Solo el creador puede eliminar este registro");

        inventoryRepo.delete(inventory);
    }

    @Override
    public List<InventoryResponse> filter(InventoryFilterRequest filters) {
        return inventoryRepo.findAll().stream()
                .filter(inv -> {
                    boolean match = true;
                    if (filters.getName() != null)
                        match &= inv.getName().toLowerCase().contains(filters.getName().toLowerCase());
                    if (filters.getStartDate() != null && filters.getEndDate() != null)
                        match &= !inv.getDateIn().isBefore(filters.getStartDate()) &&
                                !inv.getDateIn().isAfter(filters.getEndDate());
                    if (filters.getCreatedBy() != null)
                        match &= inv.getCreatedBy().getName().equalsIgnoreCase(filters.getCreatedBy());
                    return match;
                })
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private InventoryResponse toResponse(Inventory inv) {
        InventoryResponse res = new InventoryResponse();
        res.setId(inv.getId());
        res.setName(inv.getName());
        res.setQuantity(inv.getQuantity());
        res.setDateIn(inv.getDateIn());
        res.setCreatedBy(inv.getCreatedBy().getName());
        res.setModifiedBy(inv.getModifiedBy() != null ? inv.getModifiedBy().getName() : null);
        res.setDateModified(inv.getDateModified());
        return res;
    }
}
