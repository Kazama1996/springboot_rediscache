package com.kazama.jwt.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.kazama.jwt.model.Product;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Procedure("buy_product_by_productid")
    void buyProductByProductId(UUID productId);

}
