package com.example.ecommercial.service.product;

import com.example.ecommercial.domain.entity.ProductEntity;
import com.example.ecommercial.dto.ProductPostRequest;
import com.example.ecommercial.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public interface ProductService extends BaseService<ProductEntity, ProductPostRequest> {
}
