package com.abdulmo123.void_post.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "posts", schema = "post")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(nullable = false)
    private String title;

    @Basic
    @Column(nullable = false)
    private String content;

    @Basic
    @Column(nullable = false)
    private Long authorId;

    @Basic
    @Column
    private LocalDateTime crtTs;

    @Basic
    @Column
    private LocalDateTime lastUpdTs;
}
