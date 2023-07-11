package com.kazama.jwt.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kazama.jwt.dto.request.ProductRequest;
import com.kazama.jwt.model.Product;
import com.kazama.jwt.service.ProductService;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest reqBody) {
        // UUID reqUserId = (UUID)
        // SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return productService.createProduct(reqBody);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<?> buyProductBuId(@PathVariable(name = "productId") UUID productId) throws SQLException {
        return productService.buyProductById(productId);
    }

    @GetMapping("/withlock")
    public ResponseEntity<?> getProductListWithLock() {

        return productService.getProductListWithLock();
    }

    @GetMapping("/without")
    public ResponseEntity<?> getProductList() {

        return productService.getproductListWithoutLock();
    }

    @GetMapping("/dbquery")
    public ResponseEntity<?> getProductListDBQuery() {

        return productService.getProductListOnlyDBQuery();
    }

}
