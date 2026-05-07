package com.abdulmo123.void_post.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private Long authorId;
    private String authorUsername;
    private LocalDateTime crtTs;
    private LocalDateTime lastUpdTs;
}
