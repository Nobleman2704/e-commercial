package com.example.ecommercial.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Queue;

@Data
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

    @OneToMany(mappedBy = "categories", fetch = FetchType.EAGER)
    private List<ProductCategoryEntity> productCategories;

    @OneToMany(mappedBy = "categories", fetch = FetchType.EAGER)
    private List<ProductEntity> productEntities;

    @OneToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    private List<HistoryEntity> historyEntities;
}
