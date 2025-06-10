package services;

import com.nexos.inventory_system.dto.InventoryRequest;
import com.nexos.inventory_system.dto.InventoryResponse;
import com.nexos.inventory_system.models.Inventory;
import com.nexos.inventory_system.models.Role;
import com.nexos.inventory_system.models.User;
import com.nexos.inventory_system.repositories.InventoryRepository;
import com.nexos.inventory_system.repositories.UserRepository;
import com.nexos.inventory_system.services.InventoryService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InventoryServiceTest {

    @Mock private InventoryRepository inventoryRepository;
    @Mock private UserRepository userRepository;
    @Mock private SecurityContext securityContext;
    @Mock private Authentication authentication;

    @InjectMocks private InventoryService inventoryService;

    private final User mockUser = new User(
            1,
            "carloso",
            "Carlos",
            "carlos123",
            Set.of(new Role(4, "ROLE_ADMIN", "Administrador"))
    );

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(authentication.getPrincipal()).thenReturn(mockUser);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void create_shouldSaveInventoryWithAuthenticatedUser() {
        InventoryRequest request = new InventoryRequest("Filtro de aceite", 20, LocalDate.of(2024, 6, 10));

        when(inventoryRepository.existsByName("Filtro de aceite")).thenReturn(false);
        when(userRepository.findByUsername("carloso")).thenReturn(Optional.of(mockUser));
        when(inventoryRepository.save(any(Inventory.class))).thenAnswer(inv -> {
            Inventory i = inv.getArgument(0);
            i.setId(1L);
            return i;
        });

        InventoryResponse response = inventoryService.create(request);

        assertNotNull(response);
        assertEquals("Filtro de aceite", response.getName());
        assertEquals(20, response.getQuantity());
        assertEquals(LocalDate.of(2024, 6, 10), response.getDateIn());
    }

    @Test
    void update_shouldUpdateInventorySuccessfully() {
        Long id = 1L;
        Inventory inventory = new Inventory();
        inventory.setId(id);
        inventory.setName("Filtro viejo");
        inventory.setQuantity(10);
        inventory.setDateIn(LocalDate.of(2023, 1, 1));
        inventory.setCreatedBy(mockUser);

        InventoryRequest request = new InventoryRequest("Filtro nuevo", 25, LocalDate.of(2024, 6, 10));

        when(inventoryRepository.findById(id)).thenReturn(Optional.of(inventory));
        when(inventoryRepository.existsByNameAndIdNot("Filtro nuevo", id)).thenReturn(false);
        when(userRepository.findByUsername("carloso")).thenReturn(Optional.of(mockUser));
        when(inventoryRepository.save(any(Inventory.class))).thenAnswer(inv -> inv.getArgument(0));

        InventoryResponse response = inventoryService.update(id, request);

        assertEquals("Filtro nuevo", response.getName());
        assertEquals(25, response.getQuantity());
        assertEquals(LocalDate.of(2024, 6, 10), response.getDateIn());
        assertNotNull(response.getDateModified());
    }

    @Test
    void update_shouldThrowIfNotFound() {
        when(inventoryRepository.findById(999L)).thenReturn(Optional.empty());

        InventoryRequest request = new InventoryRequest("X", 1, LocalDate.now());

        assertThrows(EntityNotFoundException.class, () -> inventoryService.update(999L, request));
    }
}