package com.rest_api.fs14backend.inventory;

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

}
