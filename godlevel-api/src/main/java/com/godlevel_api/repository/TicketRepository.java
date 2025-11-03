package com.godlevel_api.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class TicketRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getTicketAverage(Long storeId, Long channelId, String startDate, String endDate) {
        String sql = "SELECT " +
                "s.store_id, " +
                "c.name AS channel_name, " +
                "COUNT(s.id) AS total_sales, " +
                "SUM(s.total_amount) AS total_amount, " +
                "SUM(s.total_amount)/COUNT(s.id) AS ticket_average " +
                "FROM sales s " +
                "JOIN channels c ON s.channel_id = c.id " +
                "WHERE 1=1 ";

        if (storeId != null) {
            sql += "AND s.store_id = " + storeId + " ";
        }
        if (channelId != null) {
            sql += "AND s.channel_id = " + channelId + " ";
        }
        if (startDate != null && endDate != null) {
            sql += "AND s.created_at BETWEEN '" + startDate + "' AND '" + endDate + "' ";
        }

        sql += "GROUP BY s.store_id, c.name " +
                "ORDER BY ticket_average DESC";

        return jdbcTemplate.queryForList(sql);
    }
}
