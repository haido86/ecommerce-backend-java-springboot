package com.rest_api.fs14backend.product;

import com.rest_api.fs14backend.category.Category;
import com.rest_api.fs14backend.category.CategoryService;
import com.rest_api.fs14backend.inventory.Inventory;
import com.rest_api.fs14backend.inventory.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getProduct() {
        return productService.getAll();
    }

    @PostMapping
    public Product createOne(@RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable Long id) {
        Product product = productService.findById(id);

        return product;
    }

    @PutMapping("/{id}")
    public Product updateOne(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        return productService.updateById(id, productRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable Long id) {
        productService.deleteById(id);
    }
}
