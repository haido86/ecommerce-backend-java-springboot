package com.rest_api.fs14backend.inventory;

import com.rest_api.fs14backend.exceptions.NotFoundException;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@NoArgsConstructor
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    public Inventory createOne(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public List<Inventory> findAll() {
        return inventoryRepository.findAll();
    }

    public Inventory findById (Long id){
        Inventory foundInventory= inventoryRepository.findById(id).orElse(null);
        if (foundInventory == null) {
            throw new NotFoundException("inventory not found");
        }
        return foundInventory;
    }

    public Inventory updateOne(Long id, int quantity){
        Inventory existingInventory= inventoryRepository.findById(id).orElse(null);
        if (existingInventory == null) {
            throw new NotFoundException("inventory not found");
        }
        existingInventory.setQuantity(quantity);
        return existingInventory;
    }
}
