package com.rest_api.fs14backend.orderItem;

import com.rest_api.fs14backend.order.Order;
import com.rest_api.fs14backend.product.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OrderItemDTO {
    private Product product;
    private Order order;
    private int quantity;
}
