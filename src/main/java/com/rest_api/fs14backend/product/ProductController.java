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

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public List<Product> getProduct() {
        return productService.getAll();
    }

    @PostMapping
    public Product createOne(@RequestBody ProductRequest productRequest) {
        ProductDTO productDTO = productRequest.getProductDTO();
        Long categoryId = productDTO.getCategoryId();
        Category category = categoryService.findById(categoryId);

        Inventory inventory = new Inventory(productRequest.getQuantity());
        Product product = productMapper.toProduct(productDTO, inventory, category);

        return productService.createProduct(product);
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable Long id) {
        Product product = productService.findById(id);

        return product;
    }

    @PutMapping("/{id}")
    public Product updateOne(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        ProductDTO productDTO = productRequest.getProductDTO();
        Category category = categoryService.findById(productRequest.getProductDTO().getCategoryId());

        Inventory inventory = new Inventory(productRequest.getQuantity());
        Product product = productMapper.toProduct(productDTO, inventory, category);

        return productService.updateById(id, product);
    }


    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable Long id) {
        productService.deleteById(id);
    }
}
