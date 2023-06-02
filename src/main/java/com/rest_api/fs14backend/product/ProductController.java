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
        String title = productRequest.getTitle();
        double price = productRequest.getPrice();
        String description = productRequest.getDescription();
        String image = productRequest.getImage();

        Category category = categoryService.findById(productRequest.getCategoryId());
        Inventory inventory = new Inventory(productRequest.getQuantity());

        ProductDTO productDTO= new ProductDTO();
        productDTO.setTitle(title);
        productDTO.setPrice(price);
        productDTO.setDescription(description);
        productDTO.setImage(image);
        productDTO.setCategoryId(category.getId());

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
        Product existingProduct = productService.findById(id);
        Category category = categoryService.findById(productRequest.getCategoryId());

        ProductDTO productDTO= new ProductDTO();
        productDTO.setTitle(productRequest.getTitle());
        productDTO.setPrice(productRequest.getPrice());
        productDTO.setDescription(productRequest.getDescription());
        productDTO.setImage(productRequest.getImage());

        Long inventoryId = existingProduct.getInventory().getId();
        Inventory inventory = inventoryService.updateOne(inventoryId, productRequest.getQuantity());
        Product product = productMapper.toProduct(productDTO, inventory, category);

        return productService.updateById(id, product);
    }


    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable Long id) {
        productService.deleteById(id);
    }
}
