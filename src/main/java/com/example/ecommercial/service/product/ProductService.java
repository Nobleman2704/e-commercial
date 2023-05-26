package com.example.ecommercial.service.product;

import com.example.ecommercial.dao.ProductDao;
import com.example.ecommercial.domain.dto.request.ProductCreateAndUpdateRequest;
import com.example.ecommercial.domain.dto.response.BaseResponse;
import com.example.ecommercial.domain.dto.response.ProductGetResponse;
import com.example.ecommercial.domain.entity.ProductEntity;
import com.example.ecommercial.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements BaseService<
        ProductCreateAndUpdateRequest,
        BaseResponse> {
    private final ProductDao productDao;
    private final ModelMapper modelMapper;

    @Override
    public BaseResponse save(ProductCreateAndUpdateRequest productCreateRequest) {
        ProductEntity product = modelMapper.map(productCreateRequest, ProductEntity.class);
        try {
            productDao.save(product);
        }catch (Exception e){
            return BaseResponse.<ProductEntity>builder()
                    .status(404)
                    .message("This name already exists")
                    .build();
        }

        return BaseResponse.builder()
                .build();
    }

    @Override
    public BaseResponse update(ProductCreateAndUpdateRequest update) {
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
    public BaseResponse<List<ProductGetResponse>> getALl() {
        return BaseResponse.<List<ProductGetResponse>>builder()
                .data(modelMapper
                        .map(productDao.findAll(),
                                new TypeToken<List<ProductGetResponse>>(){}.getType()))
                .build();
    }

}
