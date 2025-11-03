package com.godlevel_api.controller;

import com.godlevel_api.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers/inactive")
    public List<Map<String, Object>> getInactiveCustomers(
            @RequestParam int minPurchases,
            @RequestParam int daysSinceLastOrder
    ) {
        return customerService.getInactiveCustomers(minPurchases, daysSinceLastOrder);
    }
}
