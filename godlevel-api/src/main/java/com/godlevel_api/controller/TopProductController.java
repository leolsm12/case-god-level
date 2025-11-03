package com.godlevel_api.controller;

import com.godlevel_api.service.TopProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sales")
public class TopProductController {

    @Autowired
    private TopProductService service;

    @GetMapping("/top-products")
    public List<Map<String, Object>> getTopProducts(
            @RequestParam String channel,
            @RequestParam int weekday,
            @RequestParam int startHour,
            @RequestParam int endHour,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return service.getTopProductsByFilters(channel, weekday, startHour, endHour, limit);
    }
}
