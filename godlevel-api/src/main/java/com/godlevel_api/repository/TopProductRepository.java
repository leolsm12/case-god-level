package com.godlevel_api.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class TopProductRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getTopProductsByFilters(String channel, int weekdayNumber, int startHour, int endHour, int limit) {
        String sql = """
            SELECT
                p.name AS produto,
                COUNT(ps.id) AS qtd_vendas,
                SUM(ps.total_price) AS valor_total,
                SUM(ps.quantity) AS quantidade_total
            FROM product_sales ps
            JOIN sales s ON ps.sale_id = s.id
            JOIN products p ON ps.product_id = p.id
            JOIN channels c ON s.channel_id = c.id
            WHERE
                c.name ILIKE ?
                AND EXTRACT(DOW FROM s.created_at) = ?
                AND EXTRACT(HOUR FROM s.created_at) BETWEEN ? AND ?
            GROUP BY p.name
            ORDER BY qtd_vendas DESC
            LIMIT ?;
        """;

        return jdbcTemplate.queryForList(sql,
                "%" + channel + "%",
                weekdayNumber,
                startHour,
                endHour,
                limit);
    }
}
