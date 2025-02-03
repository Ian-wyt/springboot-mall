package com.ian.springbootmall.rowmapper;

import com.ian.springbootmall.model.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Order order = new Order();

        order.setOrderId(resultSet.getInt("order_id"));
        order.setUserId(resultSet.getInt("user_id"));
        order.setTotalCost(resultSet.getInt("total_cost"));
        order.setCreatedTime(resultSet.getTimestamp("created_date"));
        order.setLastModifiedTime(resultSet.getTimestamp("last_modified_date"));

        return order;
    }
}
