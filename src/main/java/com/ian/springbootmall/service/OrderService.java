package com.ian.springbootmall.service;

import com.ian.springbootmall.dto.OrderRequest;
import com.ian.springbootmall.model.Order;

public interface OrderService {

    Order getOrderById(Integer orderId);

    Integer createOrder(Integer userId, OrderRequest orderRequest);
}
