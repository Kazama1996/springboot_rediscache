package com.kazama.jwt.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kazama.jwt.model.Product;

public interface ProductRepository extends JpaRepository<Product, UUID> {

}
