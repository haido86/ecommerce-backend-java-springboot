package com.rest_api.fs14backend.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProductRequest {
    private Long categoryId;
    private String title;
    private Double price;
    private String description;
    private String image;
    private int quantity;
}
