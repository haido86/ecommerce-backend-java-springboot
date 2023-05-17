package com.rest_api.fs14backend.order;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OrderRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private OrderDTO orderDTO;
    private List<Long> productIds;
    //instead of list of productIds, we need to send list of productId and quantity as one: define cartItem {id, productId, quantity}


    public OrderRequest(OrderDTO orderDTO, List<Long> productIds) {
        this.orderDTO = orderDTO;
        this.productIds = productIds;
    }
}
