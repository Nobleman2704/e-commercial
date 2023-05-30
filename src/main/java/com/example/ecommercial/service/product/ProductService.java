package com.example.ecommercial.service.product;

import com.example.ecommercial.dao.BasketDao;
import com.example.ecommercial.dao.OrderDao;
import com.example.ecommercial.dao.ProductCategoryDao;
import com.example.ecommercial.dao.ProductDao;
import com.example.ecommercial.controller.dto.request.ProductCreateAndUpdateRequest;
import com.example.ecommercial.controller.dto.response.BaseResponse;
import com.example.ecommercial.controller.dto.response.ProductGetResponse;
import com.example.ecommercial.domain.entity.ProductCategoryEntity;
import com.example.ecommercial.domain.entity.ProductEntity;
import com.example.ecommercial.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements BaseService<
        ProductCreateAndUpdateRequest,
        BaseResponse> {
    private final ProductDao productDao;
    private final ModelMapper modelMapper;
    private final ProductCategoryDao categoryDao;
    private final OrderDao orderDao;
    private final BasketDao basketDao;

    @Override
    public BaseResponse save(ProductCreateAndUpdateRequest productCreateRequest) {
        ProductEntity product = modelMapper.map(productCreateRequest, ProductEntity.class);

        product.setCategories(categoryDao.findById(productCreateRequest.getCategoryId()).get());

        try {
            productDao.save(product);
        }catch (Exception e){
            return BaseResponse.<ProductEntity>builder()
                    .status(404)
                    .message("This name already exists")
                    .build();
        }
        BaseResponse<List<ProductGetResponse>> response = getALl(0);
        response.setMessage("saved");
        return response;
    }

    @Override
    public BaseResponse update(ProductCreateAndUpdateRequest update) {
        Long id = update.getId();
        ProductEntity product = productDao.findById(id).get();
        modelMapper.map(update, product);
        String message;
        try {
            productDao.save(product);
            message = "Updated";
        } catch (Exception e) {
            message = update.getName() + " already exists";
        }
        BaseResponse<List<ProductGetResponse>> response = getALl(0);
        response.setMessage(message);
        return response;
    }

    @Override
    public BaseResponse delete(Long id) {
        ProductEntity product = productDao.findById(id).get();
        boolean check = orderDao.existsOrderEntitiesByProducts(product);
        String message;
        int status;
        if (check){
            status = 401;
            message = "This product exists in orders";
        }else {
            basketDao.deleteBasketEntitiesByProductsId(id);
            status = 200;
            message = "product has been deleted";
            productDao.deleteById(id);
        }
        BaseResponse<List<ProductGetResponse>> response = getALl(0);
        response.setMessage(message);
        response.setStatus(status);
        return response;
    }

    @Override
    public BaseResponse<ProductGetResponse> getById(Long id) {
        ProductEntity productEntity = productDao.findById(id).get();
        return BaseResponse.<ProductGetResponse>builder()
                .status(200)
                .message("success")
                .data(modelMapper.map(productEntity, ProductGetResponse.class))
                .build();

    }

    @Override
    public BaseResponse<List<ProductGetResponse>> getALl(int pageNumber) {
        Pageable pageRequest = PageRequest.of(pageNumber, 5);
        Page<ProductEntity> productEntityPage = productDao.findAll(pageRequest);
        int totalPages = productEntityPage.getTotalPages();
        return BaseResponse.<List<ProductGetResponse>>builder()
                .totalPageAmount((totalPages==0)?0:totalPages-1)
                .data(modelMapper
                        .map(productEntityPage.getContent(),
                                new TypeToken<List<ProductGetResponse>>(){}.getType()))
                .build();
    }

    public BaseResponse<List<ProductGetResponse>> getProductsByCategoryId(Long categoryId) {
        ProductCategoryEntity category = categoryDao.findById(categoryId).get();
        List<ProductEntity> products = getCategoryProducts(category, new LinkedList<>());
        if (products.isEmpty()){
            return BaseResponse.<List<ProductGetResponse>>builder()
                    .status(404)
                    .build();
        }
        return BaseResponse.<List<ProductGetResponse>>builder()
                .status(200)
                .data(modelMapper
                        .map(products, new TypeToken<List<ProductGetResponse>>(){}
                                .getType()))
                .build();
    }

    private List<ProductEntity> getCategoryProducts(
            ProductCategoryEntity category,
            LinkedList<ProductEntity> products) {
        List<ProductEntity> productEntities = category.getProductEntities();
        List<ProductCategoryEntity> categories = category.getProductCategories();

        if (!productEntities.isEmpty()){
            products.addAll(productEntities);
        }
        if (categories.isEmpty()){
            return products;
        }
        for (ProductCategoryEntity categoryEntity : categories) {
                products.addAll(getCategoryProducts(categoryEntity, new LinkedList<>()));
        }
        return products;
    }

    public BaseResponse<List<ProductGetResponse>> changeProductAmount(Long productId, int amount) {
        ProductEntity product = productDao.findById(productId).get();
        product.setAmount(product.getAmount()+amount);
        productDao.save(product);

        BaseResponse<List<ProductGetResponse>> response = getALl(0);
        response.setMessage("Amount added");
        return response;
    }
}