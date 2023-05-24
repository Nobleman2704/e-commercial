package com.example.ecommercial.service.product;

import com.example.ecommercial.dao.ProductDao;
import com.example.ecommercial.domain.entity.ProductEntity;
import com.example.ecommercial.dto.BaseResponse;
import com.example.ecommercial.dto.ProductPostRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductDao productDao;
    private final ModelMapper modelMapper;

    @Override
    public BaseResponse<ProductEntity> save(ProductPostRequest productPostRequest) {
        ProductEntity product = modelMapper.map(productPostRequest, ProductEntity.class);

        ProductEntity save;

        try {
            save = productDao.save(product);
        }catch (Exception e){
            return BaseResponse.<ProductEntity>builder()
                    .status(404)
                    .message("This name already exists")
                    .build();
        }

        return new BaseResponse<>(200, "Sucess", save);
    }
}
