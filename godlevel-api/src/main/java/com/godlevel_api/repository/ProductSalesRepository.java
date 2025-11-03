package com.godlevel_api.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ProductSalesRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getProductsWithLowestMargin(int limit) {
        String sql = """
            SELECT 
                p.id AS product_id,
                p.name AS product_name,
                SUM(ps.total_price) AS total_revenue,
                SUM(ps.base_price * ps.quantity) AS total_cost,
                SUM(ps.total_price - ps.base_price * ps.quantity) AS total_margin,
                SUM(ps.total_price - ps.base_price * ps.quantity)/SUM(ps.total_price) AS margin_percentage
            FROM product_sales ps
            JOIN products p ON ps.product_id = p.id
            GROUP BY p.id, p.name
            ORDER BY margin_percentage ASC
            LIMIT ?
        """;

        return jdbcTemplate.queryForList(sql, limit);
    }
}
