package com.ian.springbootmall.dao;

import com.ian.springbootmall.dto.ProductRequest;
import com.ian.springbootmall.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
