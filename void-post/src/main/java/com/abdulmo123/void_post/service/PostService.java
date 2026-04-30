package com.abdulmo123.void_post.service;

import com.abdulmo123.void_post.dto.CreatePostDto;
import com.abdulmo123.void_post.dto.PostResponseDto;

import java.util.List;

public interface PostService {

    List<PostResponseDto> getAllPosts();

    PostResponseDto createPost(CreatePostDto request, String authHeader);
}
