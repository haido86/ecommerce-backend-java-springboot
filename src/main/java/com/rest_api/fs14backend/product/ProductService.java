package com.rest_api.fs14backend.product;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.rest_api.fs14backend.category.CategoryService;
import com.rest_api.fs14backend.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private CategoryService categoryService;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public String uploadAndTransformImage(String imageUrl) {
        try {
            // Upload the image to Cloudinary
            Map<String, Object> uploadResult = cloudinary.uploader().upload(imageUrl, ObjectUtils.emptyMap());

            // Generate a transformed image URL
            String transformedUrl = cloudinary.url()
                    .transformation(new Transformation().width(768).height(1152).crop("fill").gravity("center").zoom(2.0))
                    .generate(uploadResult.get("public_id").toString() + "." + uploadResult.get("format").toString());
            return transformedUrl;
        } catch (IOException e) {
            // Handle any exceptions
            return null;
        }
    }

    public Product createProduct(Product product) {
        // Upload and transform the image
        String transformImageUrl = uploadAndTransformImage(product.getImage());

        // Set the transformed image URL in the product
        product.setImage(transformImageUrl);

        // Save the product to the database
        return productRepository.save(product);
    }

    public Product findById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new NotFoundException("Product not found");
        }
        return product;
    }

    public Product updateById(Long id, Product product) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct == null) {
            throw new NotFoundException("Product not found");
        }

        existingProduct.setTitle(product.getTitle());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDescription(product.getDescription());
        String transformImageUrl = uploadAndTransformImage(product.getImage());
        existingProduct.setImage(transformImageUrl);
        existingProduct.setCategory(product.getCategory());

        return productRepository.save(existingProduct);
    }


    public void deleteById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new NotFoundException("Product not found");
        }
        productRepository.deleteById(id);
    }
}
