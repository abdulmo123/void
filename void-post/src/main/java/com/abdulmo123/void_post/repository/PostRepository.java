package com.abdulmo123.void_post.repository;

import com.abdulmo123.void_post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = """
        SELECT * FROM post.posts WHERE author_id = :authorId
    """, nativeQuery = true)
    List<Post> findPostsByUser(@Param("authorId") Long authorId);

    @Query(value = """
            SELECT * FROM post.posts p
            WHERE p.title ILIKE '%' || :keyword || '%'
            OR p.content ILIKE '%' || :keyword || '%'
    """, nativeQuery = true)
    List<Post> searchPosts(String keyword);
}
