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
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserServiceClient userServiceClient;

    @Override
    public List<PostResponseDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
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
    public PostResponseDto getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post with id {" + id + "} not found"));

        PostResponseDto dto = new PostResponseDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setAuthorId(post.getAuthorId());
        dto.setCrtTs(post.getCrtTs());
        dto.setLastUpdTs(post.getLastUpdTs());

        return dto;
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

    @Override
    public PostResponseDto deletePost(Long id, String authHeader) {
        Long authorId = userServiceClient.validate(authHeader);

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post with id {" + id + "} not found"));

        PostResponseDto dto = new PostResponseDto();
        if (authorId.equals(post.getAuthorId())) {
            dto.setId(post.getId());
            dto.setTitle(post.getTitle());
            dto.setContent(post.getContent());
            dto.setAuthorId(post.getAuthorId());
            dto.setCrtTs(post.getCrtTs());
            dto.setLastUpdTs(post.getLastUpdTs());

            postRepository.deleteById(id);
            log.info("Post with id {} successfully deleted!", id);
        } else {
            throw new RuntimeException("You are not authorized to delete this post!");
        }

        return dto;
    }
}
