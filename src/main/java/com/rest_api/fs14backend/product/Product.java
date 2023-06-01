package com.rest_api.fs14backend.product;

import com.rest_api.fs14backend.category.Category;
import com.rest_api.fs14backend.inventory.Inventory;
import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "products")
@NoArgsConstructor
public class Product {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    private String image;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="inventory_id")
    private Inventory inventory;
    @ManyToOne( optional = false)
    @JoinColumn(name="category_id")
    private Category category;

    public Product(Double price, String title, String description, String image, Inventory inventory, Category category ){
        this.price = price;
        this.title = title;
        this.description = description;
        this.image = image;
        this.inventory = inventory;
        this.category = category;
    }
}
