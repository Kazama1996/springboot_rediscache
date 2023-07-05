package com.kazama.jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kazama.jwt.dto.request.PostRequest;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    public ResponseEntity<?> createPost(@RequestBody PostRequest reqBody) {
        return null;
    }
}
