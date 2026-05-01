package com.abdulmo123.void_post.service;

import com.abdulmo123.void_post.dto.CreatePostDto;
import com.abdulmo123.void_post.dto.PostResponseDto;

import java.util.List;

public interface PostService {

    List<PostResponseDto> getAllPosts();

    PostResponseDto getPostById(Long id);

    PostResponseDto createPost(CreatePostDto request, String authHeader);

    PostResponseDto updatePost(Long id, CreatePostDto request, String authHeader);

    PostResponseDto deletePost(Long id, String authHeader);
}
