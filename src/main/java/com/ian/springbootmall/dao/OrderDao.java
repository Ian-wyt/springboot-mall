package com.ian.springbootmall.dao;

import com.ian.springbootmall.model.Order;
import com.ian.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {

    Order getOrderById(Integer orderId);

    List<OrderItem> getOrderItemsById(Integer orderId);

    Integer createOrder(Integer userId, Integer totalCost);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);
}
