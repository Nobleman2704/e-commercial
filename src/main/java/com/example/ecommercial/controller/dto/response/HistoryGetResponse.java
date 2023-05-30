package com.example.ecommercial.controller.dto.response;

import com.example.ecommercial.domain.entity.UserEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoryGetResponse {
    private Long id;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String createdBy;
    private String lastModifiedBy;
    private String name;
    private String description;
    private double totalPrice;
    private int amount;
    private String categoryName;
    private UserEntity users;
}
