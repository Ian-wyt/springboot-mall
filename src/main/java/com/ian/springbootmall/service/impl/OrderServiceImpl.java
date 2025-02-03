package com.ian.springbootmall.service.impl;

import com.ian.springbootmall.dao.OrderDao;
import com.ian.springbootmall.dao.ProductDao;
import com.ian.springbootmall.dto.BuyItem;
import com.ian.springbootmall.dto.OrderRequest;
import com.ian.springbootmall.model.Order;
import com.ian.springbootmall.model.OrderItem;
import com.ian.springbootmall.model.Product;
import com.ian.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Override
    public Order getOrderById(Integer orderId) {
        Order order = orderDao.getOrderById(orderId);

        List<OrderItem> orderItemList = orderDao.getOrderItemsById(orderId);

        order.setOrderItems(orderItemList);
        return order;
    }

    // 操作多個資料庫時，添加@Transactional，能確保同時更新多個資料庫
    @Transactional
    @Override
    public Integer createOrder(Integer userId, OrderRequest orderRequest) {
        int totalCost = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for (BuyItem buyItem : orderRequest.getBuyItems()) {
            // 計算總花費
            Product product = productDao.getProductById(buyItem.getProductId());
            int cost = product.getPrice() * buyItem.getQuantity();
            totalCost += cost;
            
            // orderItem轉換成資料庫儲存的形式
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setCost(cost);

            orderItemList.add(orderItem);
        }

        // 創建訂單
        Integer orderId = orderDao.createOrder(userId, totalCost);

        orderDao.createOrderItems(orderId, orderItemList);

        return orderId;
    }
}
