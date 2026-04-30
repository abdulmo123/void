package com.abdulmo123.void_post.service;

import com.abdulmo123.void_post.client.UserServiceClient;
import com.abdulmo123.void_post.dto.CreatePostDto;
import com.abdulmo123.void_post.dto.PostResponseDto;
import com.abdulmo123.void_post.model.Post;
import com.abdulmo123.void_post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserServiceClient userServiceClient;

    @Override
    public List<PostResponseDto> getAllPosts() {
        List<Post> posts = postRepository.getAllPosts();
        return posts.stream()
                .map(post -> {
                    PostResponseDto postResponseDto = new PostResponseDto();
                    postResponseDto.setId(post.getId());
                    postResponseDto.setTitle(post.getTitle());
                    postResponseDto.setContent(post.getContent());
                    postResponseDto.setAuthorId(post.getAuthorId());
                    postResponseDto.setCrtTs(post.getCrtTs());
                    postResponseDto.setLastUpdTs(post.getLastUpdTs());
                    return postResponseDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public PostResponseDto createPost(CreatePostDto request, String authHeader) {
        Long authorId = userServiceClient.validate(authHeader);

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setAuthorId(authorId);
        post.setCrtTs(LocalDateTime.now());
        post.setLastUpdTs(LocalDateTime.now());

        Post savedPost = postRepository.save(post);

        PostResponseDto dto = new PostResponseDto();
        dto.setId(savedPost.getId());
        dto.setTitle(savedPost.getTitle());
        dto.setContent(savedPost.getContent());
        dto.setAuthorId(authorId);
        dto.setCrtTs(savedPost.getCrtTs());
        dto.setLastUpdTs(savedPost.getLastUpdTs());

        return dto;
    }
}
