package com.example.ecommercial.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "orders")
public class OrderEntity extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity products;

    private int amount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity users;
}
