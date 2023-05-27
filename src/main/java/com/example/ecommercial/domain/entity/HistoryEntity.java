package com.example.ecommercial.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "histories")
public class HistoryEntity extends BaseEntity{
    private String name;
    private String description;
    private double price;
    private int amount;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProductCategoryEntity categories;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity users;
}
