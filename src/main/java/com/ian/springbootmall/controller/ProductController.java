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
@Tag(name = "Product Controller", description = "APIs for product CRUD operations")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "Query product", description = "Query one product record")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product data found successfully"),
            @ApiResponse(responseCode = "404", description = "Product data does not exist", content = @Content) // @Content returns an empty response body
    })
    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(
            @Parameter(description = "Product id", example = "101", required = true)
            @PathVariable Integer productId
    ) {
        Product product = productService.getProductById(productId);
        return product == null
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @Operation(summary = "Query products", description = "Query all product records")
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

        // Get product list
        List<Product> productList = productService.getProducts(productQueryParams);

        // Get total product count
        Integer total = productService.countProducts(productQueryParams);

        // Set pagination content
        Page<Product> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(productList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @Operation(summary = "Create product", description = "Create one product record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product data created successfully"),
            @ApiResponse(responseCode = "400", description = "Product data is invalid",
                    content = @Content(schema = @Schema(implementation = ProductRequest.class)))
    })
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        // Return the auto-increment id
        Integer productId = productService.createProduct(productRequest);
        Product product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @Operation(summary = "Update product", description = "Update one product record")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product data found successfully"),
            @ApiResponse(responseCode = "400", description = "Product data is invalid",
                    content = @Content(schema = @Schema(implementation = ProductRequest.class))),
            @ApiResponse(responseCode = "404", description = "Product data does not exist", content = @Content) // @Content returns an empty response body
    })
    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest) {

        // First check whether data exists for productId
        Product product = productService.getProductById(productId);
        if (product == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        productService.updateProduct(productId, productRequest);
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductById(productId));
    }

    @Operation(summary = "Delete product", description = "Delete one product record")
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Integer productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
