package com.ian.springbootmall.service.impl;

import com.ian.springbootmall.dao.ProductDao;
import com.ian.springbootmall.dto.ProductQueryParams;
import com.ian.springbootmall.dto.ProductRequest;
import com.ian.springbootmall.model.Product;
import com.ian.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        return productDao.getProducts(productQueryParams);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        productDao.updateProduct(productId, productRequest);
    }

    @Override
    public void deleteProductById(Integer productId) {
        productDao.deleteProductById(productId);
    }

    @Override
    public Integer countProducts(ProductQueryParams productQueryParams) {
        return productDao.countProducts(productQueryParams);
    }
}
