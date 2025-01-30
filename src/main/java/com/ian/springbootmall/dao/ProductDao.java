package com.ian.springbootmall.dao;

import com.ian.springbootmall.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);
}
