package com.example.ecommercial.controller.converter;

import com.example.ecommercial.controller.dto.request.CategoryCreateAndUpdateRequest;
import com.example.ecommercial.domain.entity.ProductCategoryEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryConverter {
    private final ModelMapper modelMapper;

    public ProductCategoryEntity toCategoryEntity(CategoryCreateAndUpdateRequest createAndUpdateRequest){
        return modelMapper.map(createAndUpdateRequest,ProductCategoryEntity.class);
    }
}
