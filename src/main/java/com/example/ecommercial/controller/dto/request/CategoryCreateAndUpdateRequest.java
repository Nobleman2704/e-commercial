package com.example.ecommercial.controller.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryCreateAndUpdateRequest {
    private Long id;
    @Pattern(regexp = "^[A-Z]{4,15}$",
            message = "category name length should be at least 4 and max 15 upper letters")
    private String name;
    private Long parentId;
}
