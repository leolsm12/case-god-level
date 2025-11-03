package com.godlevel_api.controller;

import com.godlevel_api.service.ProductMarginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductMarginController {

    @Autowired
    private ProductMarginService service;

    // Top N produtos com menor margem
    @GetMapping("/lowest-margin")
    public List<Map<String, Object>> getLowestMarginProducts(@RequestParam(defaultValue = "10") int limit) {
        return service.getLowestMarginProducts(limit);
    }

}
