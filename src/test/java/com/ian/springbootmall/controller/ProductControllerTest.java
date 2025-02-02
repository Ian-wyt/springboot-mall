package com.ian.springbootmall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ian.springbootmall.constant.ProductCategory;
import com.ian.springbootmall.dto.ProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // 查詢商品 success case
    @Test
    public void getProduct_success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products/{productId}", 1);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName", equalTo("Harry Potter and the Philosopher Stone")))
                .andExpect(jsonPath("$.category", equalTo("E_BOOK")))
                .andExpect(jsonPath("$.imageUrl", notNullValue()))
                .andExpect(jsonPath("$.price", notNullValue()))
                .andExpect(jsonPath("$.stock", notNullValue()))
                .andExpect(jsonPath("$.description", nullValue()))
                .andExpect(jsonPath("$.createdDate", notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate", notNullValue()));
    }

    // 查詢商品 error case
    @Test
    public void getProduct_notFound() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products/{productId}", 10000);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(404));
    }

    // 創建商品 success case
    @Transactional
    @Test
    public void createProduct_success() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName("test e_book");
        productRequest.setCategory(ProductCategory.E_BOOK);
        productRequest.setImageUrl("https://test.com");
        productRequest.setPrice(100);
        productRequest.setStock(2);

        // json to string
        String json = objectMapper.writeValueAsString(productRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.productName", equalTo("test e_book")))
                .andExpect(jsonPath("$.category", equalTo("E_BOOK")))
                .andExpect(jsonPath("$.imageUrl", equalTo("https://test.com")))
                .andExpect(jsonPath("$.price", equalTo(100)))
                .andExpect(jsonPath("$.stock", equalTo(2)))
                .andExpect(jsonPath("$.description", nullValue()))
                .andExpect(jsonPath("$.createdDate", notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate", notNullValue()));
    }

    // 創建商品 error case => 新增資料有缺失
    @Transactional
    @Test
    public void createProduct_illegalArgument() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName("test product");

        String json = objectMapper.writeValueAsString(productRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    // 更新商品 success case
    @Transactional
    @Test
    public void updateProduct_success() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName("test product");
        productRequest.setCategory(ProductCategory.CAR);
        productRequest.setImageUrl("https://test.com");
        productRequest.setPrice(10);
        productRequest.setStock(5);

        String json = objectMapper.writeValueAsString(productRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/products/{productId}", 3)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.productName", equalTo("test product")))
                .andExpect(jsonPath("$.category", equalTo("CAR")))
                .andExpect(jsonPath("$.imageUrl", equalTo("https://test.com")))
                .andExpect(jsonPath("$.price", equalTo(10)))
                .andExpect(jsonPath("$.stock", equalTo(5)))
                .andExpect(jsonPath("$.description", nullValue()))
                .andExpect(jsonPath("$.createdDate", notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate", notNullValue()));
    }

    // 更新商品 error case => 修改資料有缺失
    @Transactional
    @Test
    public void updateProduct_illegalArgument() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName("test product");

        String json = objectMapper.writeValueAsString(productRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/products/{productId}", 3)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));

    }

    // 更新商品 error case => product id 不存在
    @Transactional
    @Test
    public void updateProduct_productNotFound() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName("test product");
        productRequest.setCategory(ProductCategory.CAR);
        productRequest.setImageUrl("https://test.com");
        productRequest.setPrice(10);
        productRequest.setStock(5);

        String json = objectMapper.writeValueAsString(productRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/products/{productId}", 10000)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(404));
    }

    // 刪除商品 success case
    @Transactional
    @Test
    public void deleteProduct_success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/products/{productId}", 5);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(204));
    }

    // 刪除商品 error case => product id 不存在
    @Transactional
    @Test
    public void deleteProduct_deleteNonExistingProduct() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/products/{productId}", 20000);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(204));
    }

    // 查詢商品列表 success case
    @Test
    public void getProducts() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(5)));
    }

    // 查詢商品列表關鍵字功能 success case
    @Test
    public void getProducts_filtering() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products")
                .param("search", "B")
                .param("category", "CAR");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(2)));
    }

    // 查詢商品列表排序功能 success case
    @Test
    public void getProducts_sorting() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products")
                .param("orderBy", "price")
                .param("sort", "desc");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(5)))
                .andExpect(jsonPath("$.results[0].productId", equalTo(11)))
                .andExpect(jsonPath("$.results[1].productId", equalTo(10)))
                .andExpect(jsonPath("$.results[2].productId", equalTo(8)))
                .andExpect(jsonPath("$.results[3].productId", equalTo(9)))
                .andExpect(jsonPath("$.results[4].productId", equalTo(6)));
    }

    // 查詢商品列表分頁功能 success case
    @Test
    public void getProducts_pagination() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products")
                .param("limit", "2")
                .param("offset", "2");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(2)))
                .andExpect(jsonPath("$.results[0].productId", equalTo(5)))
                .andExpect(jsonPath("$.results[1].productId", equalTo(4)));
    }
}