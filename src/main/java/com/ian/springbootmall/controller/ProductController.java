package com.ian.springbootmall.controller;

import com.ian.springbootmall.constant.ProductCategory;
import com.ian.springbootmall.dto.ProductQueryParams;
import com.ian.springbootmall.dto.ProductRequest;
import com.ian.springbootmall.model.Product;
import com.ian.springbootmall.service.ProductService;
import com.ian.springbootmall.util.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@Tag(name = "Product Controller", description = "產品增刪改查相關的 API")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "查詢產品", description = "查詢一筆產品資料")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "成功找到產品資料"),
            @ApiResponse(responseCode = "404", description = "產品資料不存在", content = @Content) // @Content 回傳無參數的內容
    })
    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(
            @Parameter(description = "產品 id", example = "101", required = true)
            @PathVariable Integer productId
    ) {
        Product product = productService.getProductById(productId);
        return product == null
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @Operation(summary = "查詢產品", description = "建立所有產品資料")
    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(
            // Filtering
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,

            // Sorting
            @RequestParam(defaultValue = "created_date") String orderBy,
            @RequestParam(defaultValue = "desc") String sort,

            // Pagination
            @RequestParam(defaultValue = "5") @Max(2000) @Min(0) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset
    ) {
        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);

        // 取得商品列表
        List<Product> productList = productService.getProducts(productQueryParams);

        // 取得商品總筆數
        Integer total = productService.countProducts(productQueryParams);

        // 設定分頁內容
        Page<Product> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(productList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @Operation(summary = "建立產品", description = "建立一筆產品資料")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "成功建立產品資料"),
            @ApiResponse(responseCode = "400", description = "產品資料不符合",
                    content = @Content(schema = @Schema(implementation = ProductRequest.class)))
    })
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        // 回傳auto_increment的id
        Integer productId = productService.createProduct(productRequest);
        Product product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @Operation(summary = "修改產品", description = "修改一筆產品資料")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "成功找到產品資料"),
            @ApiResponse(responseCode = "400", description = "產品資料不符合",
                    content = @Content(schema = @Schema(implementation = ProductRequest.class))),
            @ApiResponse(responseCode = "404", description = "產品資料不存在", content = @Content) // @Content 回傳無參數的內容
    })
    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest) {

        // 先查詢是否有id為productId的資料
        Product product = productService.getProductById(productId);
        if (product == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        productService.updateProduct(productId, productRequest);
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductById(productId));
    }

    @Operation(summary = "刪除產品", description = "刪除一筆產品資料")
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Integer productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
