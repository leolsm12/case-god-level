package com.godlevel_api.service;

import com.godlevel_api.repository.TopProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TopProductService {

    @Autowired
    private TopProductRepository repository;

    // Alterado weekday de String para int
    public List<Map<String, Object>> getTopProductsByFilters(String channel, int weekday, int startHour, int endHour, int limit) {
        return repository.getTopProductsByFilters(channel, weekday, startHour, endHour, limit);
    }
}
