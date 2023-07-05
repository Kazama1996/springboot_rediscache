package com.kazama.jwt.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kazama.jwt.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

}
