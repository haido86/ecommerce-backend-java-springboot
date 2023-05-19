package com.rest_api.fs14backend.product;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProductDTO {
    private Long categoryId;
    private String title;
    private Double price;
    private String description;
    private String image;
}
