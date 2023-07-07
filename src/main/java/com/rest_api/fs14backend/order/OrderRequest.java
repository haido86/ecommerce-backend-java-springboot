package com.rest_api.fs14backend.order;

import com.rest_api.fs14backend.cart.CartItem;
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
    private Long userId;
    //instead of list of productIds, we need to send list of productId and quantity as one: define cartItem {id, productId, quantity}
    private List<CartItem> cartItemList;

    public OrderRequest(Long userId, List<CartItem> cartItemList) {
        this.userId = userId;
        this.cartItemList = cartItemList;
    }
}
