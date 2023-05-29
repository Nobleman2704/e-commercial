package com.example.ecommercial.service;

import org.springframework.stereotype.Service;

/**
 * @param <RP> response
 * @param <CURQ> create and update request
 */

@Service
public interface BaseService<CURQ, RP> {
     RP save(CURQ request);
     RP update(CURQ update);
     RP delete(Long id);
     RP getById(Long id);
     RP getALl(int pageNumber);
}
