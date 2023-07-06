package com.kazama.jwt.service;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kazama.jwt.dao.ProductRepository;
import com.kazama.jwt.dto.request.ProductRequest;
import com.kazama.jwt.model.Product;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ResponseEntity<?> createProduct(ProductRequest productRequest) {
        Product product = Product.builder().productName(productRequest.getProductName())
                .price(productRequest.getPrice()).description(productRequest.getDescription()).createdAt(Instant.now())
                .stock(productRequest.getStock())
                .build();

        productRepository.save(product);

        return new ResponseEntity<>("You created a product: " + product.getProductName(), HttpStatus.CREATED);

    }

}
