package com.example.ecommercial.domain.entity;

import com.example.ecommercial.domain.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "orders")
public class OrderEntity extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity products;

    private double totalPrice;
    private int amount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity users;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
