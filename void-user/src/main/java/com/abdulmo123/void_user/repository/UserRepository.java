package com.abdulmo123.void_user.repository;

import com.abdulmo123.void_user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = """
                select * from usr.users u 
                where u.email = :input or u.username = :input
            """, nativeQuery = true
    )
    Optional<User> findByUsernameOrEmail(@Param("input") String input);

    @Query(value = """
        select * from usr.users u 
        where u.id = :id
    """, nativeQuery = true)
    Optional<User> getUserProfileById(@Param("id") Long id);
}
