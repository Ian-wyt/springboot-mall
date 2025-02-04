package com.ian.springbootmall.dao;

import com.ian.springbootmall.dto.OrderQueryParams;
import com.ian.springbootmall.model.Order;
import com.ian.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {

    Order getOrderById(Integer orderId);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    List<OrderItem> getOrderItemsByOrderId(Integer orderId);

    Integer countOrders(OrderQueryParams orderQueryParams);

    Integer createOrder(Integer userId, Integer totalCost);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);
}
