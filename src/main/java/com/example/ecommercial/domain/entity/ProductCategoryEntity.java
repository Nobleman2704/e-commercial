package com.example.ecommercial.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "categories")
public class ProductCategoryEntity extends BaseEntity{
    @Column(unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private ProductCategoryEntity categories;

    @OneToMany(mappedBy = "categories", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ProductCategoryEntity> productCategories;

    @OneToMany(mappedBy = "categories", fetch = FetchType.EAGER)
    private List<ProductEntity> productEntities;
}
