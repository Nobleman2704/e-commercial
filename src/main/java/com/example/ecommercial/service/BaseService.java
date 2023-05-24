package com.example.ecommercial.service;

import com.example.ecommercial.dto.BaseResponse;
import com.example.ecommercial.dto.ProductPostRequest;
import org.springframework.stereotype.Service;

/**
 * @param <T> data
 * @param <R> request
 */

@Service
public interface BaseService<T, R> {

    BaseResponse<T> save(ProductPostRequest productPostRequest);

}
