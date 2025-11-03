package com.godlevel_api.service;

import com.godlevel_api.repository.ProductSalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductMarginService {

    @Autowired
    private ProductSalesRepository repository;

    public List<Map<String, Object>> getLowestMarginProducts(int limit) {
        return repository.getProductsWithLowestMargin(limit);
    }
}
