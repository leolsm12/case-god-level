package com.godlevel_api.service;

import com.godlevel_api.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TicketService {

    @Autowired
    private TicketRepository repository;

    public List<Map<String, Object>> getTicketAverage(Long storeId, Long channelId, String startDate, String endDate) {
        return repository.getTicketAverage(storeId, channelId, startDate, endDate);
    }
}
