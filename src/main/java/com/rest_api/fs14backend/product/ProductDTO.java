package com.rest_api.fs14backend.product;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProductDTO {
    private Long categoryId;
    private Double price;
    private String title;
    private String description;
    private String image;
    private int quantity;
}
