package com.example.ecommercial.service;

import com.example.ecommercial.domain.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * @param <BR> base response
 * @param <CURQ> create or update request
 */

@Service
public interface BaseService<CURQ, BR> {
     BR save(CURQ e);
     BR update(CURQ e);
     BR delete(Long id);
     BR getById(Long id);
     BR getALl(int pageNumber);
}
