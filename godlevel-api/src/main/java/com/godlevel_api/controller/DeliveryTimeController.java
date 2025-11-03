package com.godlevel_api.controller;

import com.godlevel_api.service.DeliveryTimeService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class DeliveryTimeController {

    private final DeliveryTimeService service;

    public DeliveryTimeController(DeliveryTimeService service) {
        this.service = service;
    }

    @GetMapping("/api/delivery/average-times/comparison")
    public ResponseEntity<Map<String, Object>> getDeliveryTimesComparison(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(required = false) Integer storeId,
            @RequestParam(required = false) String channelName
    ) {
        return ResponseEntity.ok(service.getDeliveryTimeComparison(start, end, storeId, channelName));
    }
}
