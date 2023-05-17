package com.rest_api.fs14backend.orderItem;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OrderItemDTO {
    private Long productId;
    private Long orderId;
    private int quantity;
}
