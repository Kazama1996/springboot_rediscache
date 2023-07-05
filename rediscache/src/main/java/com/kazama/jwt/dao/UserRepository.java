package com.kazama.jwt.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kazama.jwt.model.User;

@Repository
public interface UserRepository extends JpaRepository<User , UUID>{
    Optional<User> findByEmail(String email);
}
