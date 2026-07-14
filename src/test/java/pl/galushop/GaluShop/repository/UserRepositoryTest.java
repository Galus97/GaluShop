package pl.galushop.GaluShop.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import pl.galushop.GaluShop.model.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    TestEntityManager entityManager;
    @Autowired
    UserRepository userRepository;
    private User user;

    @BeforeEach
    void setUp(){
        user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@gmail.com")
                .password("{noop}password")
                .enabled(true)
                .emailCode("1111")
                .build();
        entityManager.persistAndFlush(user);
    }

    @Test
    void givenExistingEmail_whenFindByEmail_thenReturnUser(){
        //when
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        //then
        assertEquals(user, optionalUser.get());
    }

    @Test
    void givenNonExistentEmail_whenFindByEmail_thenEmptyOptional(){
        //when
        Optional<User> optionalUser = userRepository.findByEmail("example@example.pl");
        //then
        assertFalse(optionalUser.isPresent());
    }

    @Test
    void givenBlankEmail_whenFindByEmail_thenEmptyOptional(){
        //when
        Optional<User> optionalUser = userRepository.findByEmail("");
        //then
        assertFalse(optionalUser.isPresent());
    }

    @Test
    void givenNullEmail_whenFindByEmail_thenEmptyOptional(){
        //when
        Optional<User> optionalUser = userRepository.findByEmail(null);
        //then
        assertFalse(optionalUser.isPresent());
    }
}