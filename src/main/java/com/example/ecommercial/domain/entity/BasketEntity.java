package com.example.ecommercial.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "baskets")
@Builder
public class BasketEntity extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity products;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity users;

    private int productAmount;
}
