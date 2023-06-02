package com.example.ecommercial.controller.converter;

import com.example.ecommercial.controller.dto.response.OrderGetResponse;
import com.example.ecommercial.domain.entity.OrderEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class OrderConverter {

    private final ModelMapper modelMapper;

    public List<OrderGetResponse> toUserOrdersGetDto(List<OrderEntity> orderEntities) {
        return modelMapper.map(orderEntities, new TypeToken<List<OrderGetResponse>>() {}.getType());
    }

    public List<OrderGetResponse> toOrderGetDto(List<OrderEntity> orders) {
        return modelMapper
                .map(orders, new TypeToken<List<OrderGetResponse>>() {
                }.getType());
    }
}
