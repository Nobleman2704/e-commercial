package com.example.ecommercial.service.order;

import com.example.ecommercial.dao.OrderDao;
import com.example.ecommercial.domain.dto.response.BaseResponse;
import com.example.ecommercial.domain.dto.response.OrderGetResponse;
import com.example.ecommercial.domain.entity.OrderEntity;
import com.example.ecommercial.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements BaseService<OrderEntity, BaseResponse> {

    private final OrderDao orderDao;
    private final ModelMapper modelMapper;

    @Override
    public BaseResponse save(OrderEntity request) {
        return null;
    }

    @Override
    public BaseResponse update(OrderEntity update) {
        return null;
    }

    @Override
    public BaseResponse delete(Long id) {
        return null;
    }

    @Override
    public BaseResponse getById(Long id) {
        return null;
    }

    @Override
    public BaseResponse<List<OrderGetResponse>> getALl() {
        List<OrderEntity> orders = orderDao.findAll();
        return BaseResponse.<List<OrderGetResponse>>builder()
                .data(modelMapper.map(orders,
                        new TypeToken<List<OrderGetResponse>>(){}.getType()))
                .build();
    }
}
