package com.example.ecommercial.controller.converter;

import com.example.ecommercial.controller.dto.response.BasketGetResponse;
import com.example.ecommercial.domain.entity.BasketEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class BasketConverter {

    private final ModelMapper modelMapper;

    public BasketGetResponse toBasketGetDto(BasketEntity basket) {
        return modelMapper.map(basket, BasketGetResponse.class);
    }

    public List<BasketGetResponse> toBasketGetDto(List<BasketEntity> basketEntities){
        return modelMapper.map(
                basketEntities, new TypeToken<List<BasketGetResponse>>(){}.
                        getType());
    }
}
