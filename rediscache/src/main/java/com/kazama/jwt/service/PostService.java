package com.kazama.jwt.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kazama.jwt.dao.PostRepository;
import com.kazama.jwt.dao.UserRepository;
import com.kazama.jwt.dto.request.PostRequest;
import com.kazama.jwt.model.Post;
import com.kazama.jwt.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    public ResponseEntity<?> createPost(UUID reqUserId, PostRequest postRequest) {
        User reqUser = userRepository.findById(reqUserId).orElseThrow();
        Post post = Post.builder().content(postRequest.getContent()).user(reqUser).createdAt(Instant.now()).build();

        postRepository.save(post);
        return new ResponseEntity<>("You create a post", HttpStatus.CREATED);
    }
}
