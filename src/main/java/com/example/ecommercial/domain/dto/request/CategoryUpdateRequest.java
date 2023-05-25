package com.example.ecommercial.domain.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryUpdateRequest {
    private Long id;
    @Pattern(regexp = "^[A-Z]{4,15}$",
            message = "category name length should be at least 4 and max 15 upper letters")
    private String name;
}
