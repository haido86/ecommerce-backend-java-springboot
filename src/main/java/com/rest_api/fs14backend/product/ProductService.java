package com.rest_api.fs14backend.product;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.rest_api.fs14backend.category.Category;
import com.rest_api.fs14backend.category.CategoryService;
import com.rest_api.fs14backend.exceptions.NotFoundException;
import com.rest_api.fs14backend.inventory.Inventory;
import com.rest_api.fs14backend.inventory.InventoryService;
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
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private InventoryService inventoryService;

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

    public Product createProduct(ProductRequest productRequest) {
        // Upload and transform the image
        String transformImageUrl = uploadAndTransformImage(productRequest.getImage());

        Category category = categoryService.findById(productRequest.getCategoryId());
        Inventory inventory = new Inventory(productRequest.getQuantity());

        ProductDTO productDTO = new ProductDTO();
        productDTO.setTitle(productRequest.getTitle());
        productDTO.setPrice(productRequest.getPrice());
        productDTO.setDescription(productRequest.getDescription());
        productDTO.setImage(transformImageUrl);
        productDTO.setCategoryId(category.getId());

        Product product = productMapper.toProduct(productDTO, inventory, category);

        return productRepository.save(product);
    }

    public Product findById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new NotFoundException("Product not found");
        }
        return product;
    }

    public Product updateById(Long id, ProductRequest productRequest) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct == null) {
            throw new NotFoundException("Product not found");
        }
        // Transform productRequest to productDTO and then mapping to product
        Category category = categoryService.findById(productRequest.getCategoryId());

        ProductDTO productDTO = new ProductDTO();
        productDTO.setTitle(productRequest.getTitle());
        productDTO.setPrice(productRequest.getPrice());
        productDTO.setDescription(productRequest.getDescription());
        productDTO.setImage(productRequest.getImage());

        Long inventoryId = existingProduct.getInventory().getId();
        Inventory inventory = inventoryService.updateOne(inventoryId, productRequest.getQuantity());

        Product product = productMapper.toProduct(productDTO, inventory, category);

        existingProduct.setTitle(product.getTitle());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDescription(product.getDescription());
        String transformImageUrl = uploadAndTransformImage(product.getImage());
        existingProduct.setImage(transformImageUrl);
        existingProduct.setCategory(product.getCategory());
        existingProduct.setInventory(product.getInventory());

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
