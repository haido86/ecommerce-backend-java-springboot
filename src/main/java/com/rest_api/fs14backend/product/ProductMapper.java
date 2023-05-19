package com.rest_api.fs14backend.product;

import com.rest_api.fs14backend.category.Category;
import com.rest_api.fs14backend.inventory.Inventory;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product toProduct(ProductDTO product, Inventory inventory, Category category){
        return new Product(product.getPrice(), product.getTitle(), product.getDescription(), product.getImage(),inventory,category);
    }
}
