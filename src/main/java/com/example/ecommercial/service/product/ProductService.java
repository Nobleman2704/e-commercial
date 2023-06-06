package com.example.ecommercial.service.product;

import com.example.ecommercial.controller.converter.ProductConverter;
import com.example.ecommercial.controller.dto.request.ProductCreateAndUpdateRequest;
import com.example.ecommercial.dao.BasketDao;
import com.example.ecommercial.dao.OrderDao;
import com.example.ecommercial.dao.ProductCategoryDao;
import com.example.ecommercial.dao.ProductDao;
import com.example.ecommercial.controller.dto.response.BaseResponse;
import com.example.ecommercial.controller.dto.response.ProductGetResponse;
import com.example.ecommercial.domain.entity.ProductCategoryEntity;
import com.example.ecommercial.domain.entity.ProductEntity;
import com.example.ecommercial.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final ProductConverter productConverter;

    @Override
    public BaseResponse save(ProductCreateAndUpdateRequest productRequest) {
        ProductEntity product = productConverter.toProductEntity(productRequest);
        Long categoryId = productRequest.getCategoryId();

        ProductCategoryEntity category = categoryDao.findById(categoryId).get();
        product.setCategories(category);

        String message;
        int status;
        try {
            productDao.save(product);
            message = "saved";
            status = 200;
        }catch (Exception e) {
            message = "This name already exists";
            status= 404;
        }
        return BaseResponse.of(message, status);
    }

    @Override
    public BaseResponse update(ProductCreateAndUpdateRequest update) {
        ProductEntity product = productConverter.toProductEntity(update);

        Long id = product.getId();

        ProductEntity product1 = productDao.findById(id).get();

        modelMapper.map(product, product1);

        String message;
        int status;

        try {
            productDao.save(product1);
            message = "Updated";
            status = 200;
        } catch (Exception e) {
            message = update.getName() + " already exists";
            status = 401;
        }
        return BaseResponse.of(message, status);
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
        ProductEntity product = productDao.findById(id).get();

        return BaseResponse.of(
                "success",
                200,
                productConverter.toProductGetDto(product));
    }

    @Override
    public BaseResponse<List<ProductGetResponse>> getALl(int pageNumber) {
        Pageable pageRequest = PageRequest.of(pageNumber, 5);
        Page<ProductEntity> productEntityPage = productDao.findAll(pageRequest);
        int totalPages = productEntityPage.getTotalPages();

        return BaseResponse.of(
                "success",
                200,
                productConverter.toProductGetDto(productEntityPage.getContent()),
                (totalPages==0)?0:totalPages-1);
    }

    public BaseResponse<List<ProductGetResponse>> getProductsByCategoryId(Long categoryId) {
        ProductCategoryEntity category = categoryDao.findById(categoryId).get();
        List<ProductEntity> products = getCategoryProducts(category, new LinkedList<>());
        if (products.isEmpty()){
            return BaseResponse.of("empty", 404);
        }
        return BaseResponse.of(
                "success",
                200,
                productConverter.toProductGetDto(products));
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