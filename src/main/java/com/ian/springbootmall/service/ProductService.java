package com.ian.springbootmall.service;

import com.ian.springbootmall.dto.ProductRequest;
import com.ian.springbootmall.model.Product;

public interface ProductService {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);
}
