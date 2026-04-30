package com.abdulmo123.void_post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private Long authorId;
    private LocalDateTime crtTs;
    private LocalDateTime lastUpdTs;
}
