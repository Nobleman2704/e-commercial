package com.example.ecommercial.service.order;

import com.example.ecommercial.dao.BasketDao;
import com.example.ecommercial.dao.OrderDao;
import com.example.ecommercial.dao.UserDao;
import com.example.ecommercial.domain.dto.response.BaseResponse;
import com.example.ecommercial.domain.dto.response.BasketGetResponse;
import com.example.ecommercial.domain.dto.response.OrderGetResponse;
import com.example.ecommercial.domain.entity.BasketEntity;
import com.example.ecommercial.domain.entity.OrderEntity;
import com.example.ecommercial.domain.entity.ProductEntity;
import com.example.ecommercial.domain.entity.UserEntity;
import com.example.ecommercial.domain.enums.OrderStatus;
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
    private final BasketDao basketDao;
    private final UserDao userDao;

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

    public BaseResponse<BasketGetResponse> orderProduct(Long basketId) {
        BasketEntity basket = basketDao.findById(basketId).get();
        UserEntity user = basket.getUsers();
        ProductEntity product = basket.getProducts();

        int status;
        String message;

        double balance = user.getBalance();
        int basketAmount = basket.getProductAmount();
        double price = product.getPrice();
        int productAmount = product.getAmount();

        double totalPrice = price * basketAmount;
        BasketGetResponse basketGetResponse = modelMapper
                .map(basket, BasketGetResponse.class);
        if (basketAmount > productAmount){
            status = 401;
            message = "Your ordered amount is greater than total product amount";
        } else if (totalPrice > balance) {
            status = 402;
            message = "You do not have enough balance";
        }else {
            OrderEntity order = OrderEntity.builder()
                    .amount(basketAmount)
                    .products(product)
                    .totalPrice(totalPrice)
                    .users(user)
                    .orderStatus(OrderStatus.NEW)
                    .build();
            orderDao.save(order);
            user.setBalance(user.getBalance()-totalPrice);
            product.setAmount(product.getAmount()-basketAmount);
            basketDao.deleteById(basket.getId());
            status = 200;
            message = "Product has been ordered";
            basketGetResponse = null;
        }
        return BaseResponse.<BasketGetResponse>builder()
                .status(status)
                .message(message)
                .data(basketGetResponse)
                .build();
    }

    public BaseResponse<List<OrderGetResponse>> findUserOrders(Long chatId) {
        UserEntity userEntity = userDao.findUserEntitiesByChatId(chatId).get();
        List<OrderEntity> orders = userEntity.getOrderEntities();
        if (orders.isEmpty()){
            return BaseResponse.<List<OrderGetResponse>>builder()
                    .status(404)
                    .build();
        }
        return BaseResponse.<List<OrderGetResponse>>builder()
                .status(200)
                .data(modelMapper
                        .map(orders, new TypeToken<List<OrderGetResponse>>(){}.getType()))
                .build();
    }
}
