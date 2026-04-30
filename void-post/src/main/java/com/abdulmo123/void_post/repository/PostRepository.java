package com.abdulmo123.void_post.repository;

import com.abdulmo123.void_post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = """
            select * from post.posts p
        """, nativeQuery = true)
    List<Post> getAllPosts();
}
