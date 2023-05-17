package com.rest_api.fs14backend.orderItem;

import com.rest_api.fs14backend.order.Order;
import com.rest_api.fs14backend.product.Product;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orderItems")
@Data
@NoArgsConstructor
public class OrderItem {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column()
    private int quantity;
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderItem(int quantity, Product product, Order order) {
        this.quantity = quantity;
        this.product = product;
        this.order = order;
    }
}
