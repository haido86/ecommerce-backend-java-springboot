package com.rest_api.fs14backend.product;

import com.rest_api.fs14backend.category.Category;
import com.rest_api.fs14backend.exceptions.NotFoundException;

import com.rest_api.fs14backend.category.CategoryService;
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

    @GetMapping
    public List<Product> getProduct() {
        return productService.getAll();
    }

    @PostMapping
    public Product createOne(@RequestBody ProductDTO productDTO) {
        Long categoryId = productDTO.getCategoryId();
        Category category = categoryService.findById(categoryId);
        if (category == null) {
            throw new NotFoundException("Category not found");
        }
        Product product = productMapper.toProduct(productDTO, category);
        return productService.createProduct(product);
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable Long id) {
        Product product = productService.findById(id);
        return product;
    }

    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable Long id) {
        Product product = productService.findById(id);
        productService.deleteById(id);
    }
}
