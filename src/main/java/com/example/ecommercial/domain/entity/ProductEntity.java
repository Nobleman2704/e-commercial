package com.example.ecommercial.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "products")
public class ProductEntity extends BaseEntity{
    @Column(unique = true)
    private String name;
    private String description;
    private double price;
    private int amount;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProductCategoryEntity categories;

    @OneToMany(mappedBy = "products", cascade = CascadeType.ALL)
    private List<OrderEntity> orderEntities;
}
