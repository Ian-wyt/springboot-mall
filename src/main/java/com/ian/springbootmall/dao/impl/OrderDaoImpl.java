package com.ian.springbootmall.dao.impl;

import com.ian.springbootmall.dao.OrderDao;
import com.ian.springbootmall.dto.OrderQueryParams;
import com.ian.springbootmall.model.Order;
import com.ian.springbootmall.model.OrderItem;
import com.ian.springbootmall.rowmapper.OrderItemRowMapper;
import com.ian.springbootmall.rowmapper.OrderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Order getOrderById(Integer orderId) {
        String sql = "SELECT order_id, user_id, total_cost, created_date, last_modified_date " +
                "FROM `order` WHERE order_id = :orderId";

        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);

        List<Order> orderList = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());

        if (!orderList.isEmpty()){
            return orderList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {
        String sql = "SELECT order_id, user_id, total_cost, created_date, last_modified_date " +
                "FROM `order` WHERE user_id = :userId ";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", orderQueryParams.getUserId());

        // sorting
        sql = sql + " ORDER BY created_date DESC";

        // pagination
        sql = sql + " LIMIT :limit OFFSET :offset";
        map.put("limit", orderQueryParams.getLimit());
        map.put("offset", orderQueryParams.getOffset());

        List<Order> orderList = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());

        return orderList;
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {
        String sql = "SELECT oi.order_item_id, oi.order_id, oi.product_id, oi.quantity, oi.cost, " +
                "p.product_name, p.image_url, p.price FROM order_item as oi " +
                "LEFT JOIN product as p ON oi.product_id = p.product_id " +
                "WHERE oi.order_id = :orderId";

        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);

        List<OrderItem> orderItemList = namedParameterJdbcTemplate.query(sql, map, new OrderItemRowMapper());

        if (!orderItemList.isEmpty()){
            return orderItemList;
        } else {
            return null;
        }
    }

    @Override
    public Integer countOrders(OrderQueryParams orderQueryParams) {
        String sql = "SELECT count(*) FROM `order` WHERE user_id = :userId";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", orderQueryParams.getUserId());

        return namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
    }

    @Override
    public Integer createOrder(Integer userId, Integer totalCost) {
        String sql = "INSERT INTO `order` (user_id, total_cost, created_date, last_modified_date) " +
                "VALUES (:userId, :totalCost, :createdDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("totalCost", totalCost);

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int orderId = Objects.requireNonNull(keyHolder.getKey()).intValue();

        return orderId;
    }

    @Override
    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {
        String sql = "INSERT INTO order_item(order_id, product_id, quantity, cost) " +
                "VALUES (:orderId, :productId, :quantity, :cost)";

        // 使用batchUpdate一次性加入數據
        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[orderItemList.size()];

        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);

            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("orderId", orderId);
            parameterSources[i].addValue("productId", orderItem.getProductId());
            parameterSources[i].addValue("quantity", orderItem.getQuantity());
            parameterSources[i].addValue("cost", orderItem.getCost());
        }

        namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);
    }
}
