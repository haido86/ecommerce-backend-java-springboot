package com.rest_api.fs14backend.order;

import com.rest_api.fs14backend.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "purchase_at")
    @CreationTimestamp
    private Date purchaseAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Order(User user) {
        this.user = user;
    }
}
