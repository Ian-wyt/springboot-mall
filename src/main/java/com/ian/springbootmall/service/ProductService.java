package com.ian.springbootmall.service;

import com.ian.springbootmall.dto.ProductQueryParams;
import com.ian.springbootmall.dto.ProductRequest;
import com.ian.springbootmall.model.Product;

import java.util.List;

public interface ProductService {

    Product getProductById(Integer productId);

    List<Product> getProducts(ProductQueryParams productQueryParams);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);

    Integer countProducts(ProductQueryParams productQueryParams);
}
