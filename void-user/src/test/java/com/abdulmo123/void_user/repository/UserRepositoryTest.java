package com.abdulmo123.void_user.repository;

import com.abdulmo123.void_user.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUsername("johndoe");
        testUser.setEmail("john@example.com");
        testUser.setPassword("hashedpassword");
        userRepository.save(testUser);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void findByEmailOrUsername_withEmail_returnsUser() {
        Optional<User> result = userRepository.findByUsernameOrEmail("john@example.com");
        assertTrue(result.isPresent());
        assertEquals("johndoe", result.get().getUsername());
    }

    @Test
    void findByEmailOrUsername_withUsername_returnsUser() {
        Optional<User> result = userRepository.findByUsernameOrEmail("johndoe");
        assertTrue(result.isPresent());
        assertEquals("john@example.com", result.get().getEmail());
    }

    @Test
    void findByEmailOrUsername_withInvalidInput_returnsEmpty() {
        Optional<User> result = userRepository.findByUsernameOrEmail("nobody@example.com");
        assertFalse(result.isPresent());
    }

    @Test
    void save_persistsUserCorrectly() {
        User newUser = new User();
        newUser.setUsername("janedoe");
        newUser.setEmail("jane@example.com");
        newUser.setPassword("hashedpassword");

        User saved = userRepository.save(newUser);

        assertNotNull(saved.getId());
        assertEquals("janedoe", saved.getUsername());
    }
}
