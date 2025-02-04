package com.ian.springbootmall.service;

import com.ian.springbootmall.dto.OrderQueryParams;
import com.ian.springbootmall.dto.OrderRequest;
import com.ian.springbootmall.model.Order;
import com.ian.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderService {

    Order getOrderById(Integer orderId);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Integer countOrders(OrderQueryParams orderQueryParams);

    Integer createOrder(Integer userId, OrderRequest orderRequest);
}
