package com.godlevel_api.controller;

import com.godlevel_api.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TicketController {

    @Autowired
    private TicketService service;

    @GetMapping("/ticket-average")
    public List<Map<String, Object>> getTicketAverage(
            @RequestParam(required = false) Long storeId,
            @RequestParam(required = false) Long channelId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate
    ) {
        return service.getTicketAverage(storeId, channelId, startDate, endDate);
    }
}
