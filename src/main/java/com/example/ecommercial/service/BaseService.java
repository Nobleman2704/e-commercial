package com.example.ecommercial.service;

import org.springframework.stereotype.Service;

/**
 * @param <RP> response
 * @param <CRQ> create request
 * @param <URQ> update request
 */

@Service
public interface BaseService<CRQ, RP, URQ> {
     RP save(CRQ request);
     RP update(URQ update);
     RP delete(Long id);
     RP getById(Long id);
     RP getALl();
}
