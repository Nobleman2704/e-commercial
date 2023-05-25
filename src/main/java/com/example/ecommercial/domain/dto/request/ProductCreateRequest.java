package com.example.ecommercial.domain.dto.request;

import com.example.ecommercial.domain.entity.OrderEntity;
import com.example.ecommercial.domain.entity.ProductCategoryEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateRequest {
    @Pattern(regexp = "^([a-zA-Z0-9]|\\s){4,30}$",
            message = "product name length should be at least 4 max 30 characters " +
                    "and it should only satisfy [a-zA-Z0-9] pattern")
    private String name;
    @Pattern(regexp = "^(\\w|\\s){1,500}$",
            message = "description should only satisfy [a-zA-Z0-9] pattern")
    private String description;
    private double price;
    private int amount;

    private ProductCategoryEntity categories;

    private List<OrderEntity> orderEntities;
}
