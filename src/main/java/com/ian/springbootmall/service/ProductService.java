package com.ian.springbootmall.service;

import com.ian.springbootmall.constant.ProductCategory;
import com.ian.springbootmall.dto.ProductRequest;
import com.ian.springbootmall.model.Product;

import java.util.List;

public interface ProductService {

    Product getProductById(Integer productId);

    List<Product> getProducts(ProductCategory category, String search);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
