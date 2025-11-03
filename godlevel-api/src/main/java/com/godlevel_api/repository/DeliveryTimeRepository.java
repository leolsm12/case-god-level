package com.godlevel_api.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public class DeliveryTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public DeliveryTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> getDeliveryTimes(LocalDateTime start, LocalDateTime end, Integer storeId, String channelName) {
        String sql = """
                SELECT
                    EXTRACT(DOW FROM s.created_at) AS weekday,
                    EXTRACT(HOUR FROM s.created_at) AS hour,
                    AVG(s.delivery_seconds) AS avg_delivery_seconds
                FROM sales s
                JOIN channels c ON s.channel_id = c.id
                WHERE s.delivery_seconds IS NOT NULL
                  AND s.created_at >= ?
                  AND s.created_at <= ?
                  AND (CAST(? AS BIGINT) IS NULL OR s.store_id = CAST(? AS BIGINT))
                  AND (CAST(? AS TEXT) IS NULL OR c.name ILIKE CAST(? AS TEXT))
                GROUP BY weekday, hour
                ORDER BY weekday, hour
            """;

        return jdbcTemplate.queryForList(
                sql,
                start,
                end,
                storeId, storeId,
                channelName, channelName
        );
    }
}
