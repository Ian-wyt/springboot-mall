package com.ian.springbootmall.service.impl;

import com.ian.springbootmall.dao.OrderDao;
import com.ian.springbootmall.dao.ProductDao;
import com.ian.springbootmall.dao.UserDao;
import com.ian.springbootmall.dto.BuyItem;
import com.ian.springbootmall.dto.OrderQueryParams;
import com.ian.springbootmall.dto.OrderRequest;
import com.ian.springbootmall.model.Order;
import com.ian.springbootmall.model.OrderItem;
import com.ian.springbootmall.model.Product;
import com.ian.springbootmall.model.User;
import com.ian.springbootmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Override
    public Order getOrderById(Integer orderId) {
        Order order = orderDao.getOrderById(orderId);

        List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);

        order.setOrderItems(orderItemList);
        return order;
    }

    @Override
    public Integer countOrders(OrderQueryParams orderQueryParams) {
        return orderDao.countOrders(orderQueryParams);
    }

    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {
        List<Order> orderList = orderDao.getOrders(orderQueryParams);

        for (Order order : orderList) {
            List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(order.getOrderId());

            order.setOrderItems(orderItemList);
        }

        return orderList;
    }

    // 操作多個資料庫時，添加@Transactional，能確保同時更新多個資料庫
    @Transactional
    @Override
    public Integer createOrder(Integer userId, OrderRequest orderRequest) {
        // 檢查user是否存在
        User user = userDao.getUserById(userId);

        if (user == null) {
            log.warn("The userId {} is not exist", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int totalCost = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for (BuyItem buyItem : orderRequest.getBuyItems()) {
            Product product = productDao.getProductById(buyItem.getProductId());

            if (product == null) {
                log.warn("The productId {} is not exist", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } else if (product.getStock() < buyItem.getQuantity()) {
                log.warn("The stock quantity of Product {} is insufficient", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            // 更新商品庫存
            productDao.updateStock(product.getProductId(), product.getStock() - buyItem.getQuantity());

            // 計算總花費
            int cost = product.getPrice() * buyItem.getQuantity();
            totalCost += cost;
            
            // buyItem 轉換成 orderItem
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
