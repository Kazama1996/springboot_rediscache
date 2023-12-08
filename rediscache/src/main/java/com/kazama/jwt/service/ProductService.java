package com.kazama.jwt.service;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kazama.jwt.dao.ProductRepository;
import com.kazama.jwt.dto.request.ProductRequest;
import com.kazama.jwt.model.Product;
import com.kazama.jwt.util.InstantTypeAdapter;

@Service
public class ProductService {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    private ProductRepository productRepository;

    private Gson gson;

    Type productListType;

    private String jsonString;

    private String updatedJsonStr;

    private List<Product> productList;

    public ProductService() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, new InstantTypeAdapter())
                .create();

        this.productListType = new TypeToken<List<Product>>() {
        }.getType();
        this.productList = new ArrayList<Product>();
    }

    public ResponseEntity<?> createProduct(ProductRequest productRequest) {
        Product product = Product.builder().productName(productRequest.getProductName())
                .price(productRequest.getPrice()).description(productRequest.getDescription()).createdAt(Instant.now())
                .stock(productRequest.getStock())
                .build();

        productRepository.save(product);

        return new ResponseEntity<>("You created a product: " + product.getProductName(), HttpStatus.CREATED);

    }

    public ResponseEntity<?> buyProductById(UUID productId) throws SQLException {

        productRepository.buyProductByProductId(productId);
        return new ResponseEntity<>("You buy a product: ", HttpStatus.OK);

    }

    public ResponseEntity<?> getProductListWithLock() {

        ValueOperations<Object, Object> valueOperations = redisTemplate.opsForValue();

        this.jsonString = (String) redisTemplate.opsForValue().get("ProductList");

        if (jsonString != null) {
            this.productList = gson.fromJson(jsonString, productListType);
            return ResponseEntity.ok().body(this.productList);
        }
        boolean lockAcqired = valueOperations.setIfAbsent("cacheLock", true, 5,
                TimeUnit.SECONDS);

        try {
            if (lockAcqired) {
                this.jsonString = (String) valueOperations.get("ProductList");
                if (jsonString != null) {
                    this.productList = gson.fromJson(jsonString, productListType);
                    return ResponseEntity.ok().body(this.productList);
                }

                this.productList = productRepository.findAll();
                this.updatedJsonStr = gson.toJson(this.productList);
                valueOperations.set("ProductList", this.updatedJsonStr);
                return ResponseEntity.ok().body(this.productList);
            } else {
                return ResponseEntity.status(HttpStatusCode.valueOf(503)).body("Service is not ready please try again");
            
            }
        } finally {
            redisTemplate.delete("cacheLock");
        }

        // if (lockAcqired) {
        // try {
        // jsonString = (String) redisTemplate.opsForValue().get("ProductList");
        // if (jsonString != null) {
        // this.productList = gson.fromJson(jsonString, productListType);
        // return ResponseEntity.ok().body(this.productList);
        // }
        // this.productList = productRepository.findAll();
        // this.updatedJsonStr = gson.toJson(this.productList);
        // redisTemplate.opsForValue().set("ProductList", this.updatedJsonStr);
        // return new ResponseEntity<>(productList, HttpStatus.OK);
        // } finally {
        // redisTemplate.delete("cacheLock");
        // }
        // }
        // return new ResponseEntity<>(productList, HttpStatus.OK);

    }

    public ResponseEntity<?> getProductListOnlyDBQuery() {
        this.productList = productRepository.findAll();
        return new ResponseEntity<>(this.productList, HttpStatus.OK);
    }

    public ResponseEntity<?> getproductListWithoutLock() {

        this.jsonString = (String) redisTemplate.opsForValue().get("ProductList");
        if (jsonString != null) {
            this.productList = gson.fromJson(jsonString, productListType);
            return ResponseEntity.ok().body(this.productList);
        } else {
            this.productList = productRepository.findAll();
            this.updatedJsonStr = gson.toJson(productList);
            redisTemplate.opsForValue().set("ProductList", this.updatedJsonStr);
            return new ResponseEntity<>(productList, HttpStatus.OK);
        }
    }

}
