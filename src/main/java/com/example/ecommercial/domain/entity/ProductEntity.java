package com.example.ecommercial.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
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
    @JoinColumn(name = "product_id")
    private ProductCategoryEntity categories;

    @OneToMany(mappedBy = "products", cascade = CascadeType.ALL)
    private List<OrderEntity> orderEntities;
}
