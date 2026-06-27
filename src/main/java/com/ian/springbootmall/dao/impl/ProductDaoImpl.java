package com.ian.springbootmall.dao.impl;

import com.ian.springbootmall.dao.ProductDao;
import com.ian.springbootmall.dto.ProductQueryParams;
import com.ian.springbootmall.dto.ProductRequest;
import com.ian.springbootmall.model.Product;
import com.ian.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductById(Integer productId) {
        String sql = "SELECT * FROM product WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        // RowMapper converts database query results into Java objects.
        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        if (productList.isEmpty()) {
            return null;
        } else {
            return productList.get(0);
        }
    }

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        // WHERE 1=1 makes it easier to append query conditions dynamically.
        String sql = "SELECT * FROM product WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        sql = addFilteringSql(sql, map, productQueryParams);

        // Set the sort expression through string concatenation; it cannot be supplied through the map.
        sql += " ORDER BY " + productQueryParams.getOrderBy() + " " + productQueryParams.getSort();

        // Set the number of returned rows and how many rows to skip.
        sql += " LIMIT " + productQueryParams.getLimit() + " OFFSET " + productQueryParams.getOffset();

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        return productList;
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql = "INSERT INTO product(product_name, category, image_url, " +
                "price, stock, description, created_date, last_modified_date) " +
                "VALUES (:productName, :category, :imageUrl, :price, :stock, " +
                ":description, :createdDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());  // Remember to convert enum values to strings.
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        // Store the generated id.
        KeyHolder keyHolder = new GeneratedKeyHolder();

        // Use MapSqlParameterSource when SQL queries or inserts have many parameters, complex types, or dynamic parameters.
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int productId = Objects.requireNonNull(keyHolder.getKey()).intValue();

        return productId;
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        String sql = "UPDATE product SET product_name = :productName, category = :category, " +
                "image_url = :imageUrl, price = :price, stock = :stock, description = :description, " +
                "last_modified_date = :lastModifiedDate WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());
        map.put("lastModifiedDate", new Date());

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void updateStock(Integer productId, Integer stock) {
        String sql ="UPDATE product SET stock = :stock, last_modified_date = :lastModifiedDate " +
                "WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("stock", stock);
        map.put("lastModifiedDate", new Date());
        map.put("productId", productId);

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void deleteProductById(Integer productId) {
        String sql = "DELETE FROM product WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public Integer countProducts(ProductQueryParams productQueryParams) {
        String sql = "SELECT count(*) FROM product WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        sql = addFilteringSql(sql, map, productQueryParams);

        // Use queryForObject and convert the value directly to Integer.class.
        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);

        return total;
    }

    // Set SQL query conditions.
    private String addFilteringSql(String sql, Map<String, Object> map, ProductQueryParams productQueryParams) {

        // Add category to filter data.
        if (productQueryParams.getCategory() != null) {
            sql += " AND category = :category";
            map.put("category", productQueryParams.getCategory().name()); // Use name() to get the string value for enum types.
        }

        // Filter data by the user's search keyword.
        if (productQueryParams.getSearch() != null) {
            sql += " AND product_name LIKE :search";
            map.put("search", "%" + productQueryParams.getSearch() + "%"); // % is used for fuzzy matching.
        }

        return sql;
    }
}
