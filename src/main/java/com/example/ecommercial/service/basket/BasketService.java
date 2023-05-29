package com.example.ecommercial.service.basket;

import com.example.ecommercial.dao.BasketDao;
import com.example.ecommercial.dao.ProductDao;
import com.example.ecommercial.dao.UserDao;
import com.example.ecommercial.controller.dto.response.BaseResponse;
import com.example.ecommercial.controller.dto.response.BasketGetResponse;
import com.example.ecommercial.domain.entity.BasketEntity;
import com.example.ecommercial.domain.entity.ProductEntity;
import com.example.ecommercial.domain.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasketService{


    private final ProductDao productDao;
    private final UserDao userDao;
    private final BasketDao basketDao;
    private final ModelMapper modelMapper;

    public BaseResponse delete(Long id) {
        basketDao.deleteById(id);
        return new BaseResponse();
    }
    public BaseResponse<BasketGetResponse> getById(Long id) {
        BasketEntity basketEntity = basketDao.findById(id).get();
        return BaseResponse.<BasketGetResponse>builder()
                .data(modelMapper.map(basketEntity, BasketGetResponse.class))
                .build();
    }


    public void save(String data, Long chatId) {
        String[] split = data.split(" ");
        int amount = Integer.parseInt(split[0]);
        Long productId = Long.valueOf(split[1]);

        Optional<BasketEntity> optionalBasket = basketDao
                .findBasketEntitiesByUsersChatIdAndProductsId(chatId, productId);

        if (optionalBasket.isEmpty()){
            ProductEntity product = productDao.findById(productId).get();
            UserEntity userEntity = userDao.findUserEntitiesByChatId(chatId).get();
            BasketEntity basketEntity = BasketEntity.builder()
                    .products(product)
                    .users(userEntity)
                    .productAmount(amount)
                    .build();
            basketDao.save(basketEntity);
            return;
        }
        BasketEntity basketEntity = optionalBasket.get();
        basketEntity.setProductAmount(basketEntity.getProductAmount()+amount);
        basketDao.save(basketEntity);
    }

    public BaseResponse<List<BasketGetResponse>> getUserBaskets(Long chatId) {
        List<BasketEntity> baskets = basketDao
                .findBasketEntitiesByUsersChatId(chatId).get();
        if (baskets.isEmpty()){
            return BaseResponse.<List<BasketGetResponse>>builder()
                    .status(404)
                    .build();
        }
        return BaseResponse.<List<BasketGetResponse>>builder()
                .status(200)
                .data(modelMapper.map(baskets, new TypeToken<List<BasketGetResponse>>(){}
                        .getType()))
                .build();
    }

    public BaseResponse<BasketGetResponse> modifyBasket(int amount, Long basketId) {
        BasketEntity basket = basketDao.findById(basketId).get();
        
        if (basket.getProductAmount() + amount==0){
            return BaseResponse.<BasketGetResponse>builder()
                    .message("Minimum amount is 1")
                    .data(modelMapper.map(basket, BasketGetResponse.class))
                    .build();
        }
        basket.setProductAmount(basket.getProductAmount()+amount);
        basketDao.save(basket);
        
        return BaseResponse.<BasketGetResponse>builder()
                .data(modelMapper.map(basket, BasketGetResponse.class))
                .message("Amount has been changed")
                .build();
    }
}
