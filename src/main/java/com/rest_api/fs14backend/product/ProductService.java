package com.rest_api.fs14backend.product;

import com.rest_api.fs14backend.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAll() { return productRepository.findAll(); }

    public Product createProduct(Product product) { return productRepository.save(product); }

    public Product findById(Long id) {
        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            throw new NotFoundException("Product not found");
        }
        return product;
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
