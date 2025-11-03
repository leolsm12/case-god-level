package com.godlevel_api.service;

import com.godlevel_api.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Map<String, Object>> getInactiveCustomers(int minPurchases, int daysSinceLastOrder) {
        return customerRepository.getInactiveCustomers(minPurchases, daysSinceLastOrder);
    }
}
