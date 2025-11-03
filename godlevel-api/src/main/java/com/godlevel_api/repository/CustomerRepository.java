package com.godlevel_api.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    public CustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> getInactiveCustomers(int minPurchases, int daysSinceLastOrder) {

        String sql = """
            SELECT
                c.id AS customer_id,
                c.customer_name AS name,
                c.email AS email,
                COUNT(s.id) AS total_orders,
                MAX(s.created_at) AS last_order_date,
                EXTRACT(DAY FROM (NOW() - MAX(s.created_at))) AS days_since_last_order
            FROM customers c
            JOIN sales s ON s.customer_id = c.id
            GROUP BY c.id, c.customer_name, c.email
            HAVING COUNT(s.id) >= ?
               AND MAX(s.created_at) < NOW() - INTERVAL '? days'
            ORDER BY total_orders DESC
        """;

        // OBS: jdbcTemplate não substitui INTERVAL direto, então concatenamos
        String sqlFinal = sql.replace("INTERVAL '? days'", "INTERVAL '" + daysSinceLastOrder + " days'");

        return jdbcTemplate.queryForList(sqlFinal, minPurchases);
    }
}
