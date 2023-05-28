package com.example.ecommercial.domain.dto.response;

import com.example.ecommercial.domain.entity.OrderEntity;
import com.example.ecommercial.domain.entity.ProductCategoryEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductGetResponse {
    Long id;
    LocalDateTime createdDate;
    LocalDateTime updatedDate;
    String createdBy;
    String lastModifiedBy;
    String name;
    String description;
    double price;
    int amount;
    ProductCategoryEntity categories;
}
