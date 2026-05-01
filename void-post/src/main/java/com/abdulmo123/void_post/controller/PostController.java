package com.abdulmo123.void_post.controller;

import com.abdulmo123.void_post.dto.CreatePostDto;
import com.abdulmo123.void_post.dto.PostResponseDto;
import com.abdulmo123.void_post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/all")
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());
    }

    @GetMapping("find/{id}")
    public ResponseEntity<PostResponseDto> findPostById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<PostResponseDto> createPost(
            @RequestBody CreatePostDto request,
            @RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(request, authHeader));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable("id") Long id,
            @RequestBody CreatePostDto request,
            @RequestHeader("Authorization") String authHeader) {
                return ResponseEntity.status(HttpStatus.OK).body(postService.updatePost(id, request, authHeader));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<PostResponseDto> deletePost(
            @PathVariable("id") Long id,
            @RequestHeader("Authorization") String authHeader) {
                return ResponseEntity.status(HttpStatus.OK).body(postService.deletePost(id, authHeader));
    }

    @GetMapping("/user/{authorId}")
    public ResponseEntity<List<PostResponseDto>> getUserPosts(@PathVariable("authorId") Long authorId) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.findUserPosts(authorId));
    }
}
