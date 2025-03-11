package pl.galushop.GaluShop.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.entity.UserData;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserDataRepositoryTest {

    @Autowired
    TestEntityManager entityManager;
    @Autowired
    UserDataRepository userDataRepository;
    private UserData userData;

    @BeforeEach
    void setUp(){
        User persistedUser = entityManager.persistAndFlush(User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@gmail.com")
                .password("{noop}secretPassword")
                .enabled(true)
                .emailCode("1111")
                .build());

        userData = UserData.builder()
                .city("Warsaw")
                .street("Pulawska")
                .streetNumber(10)
                .apartmentNumber(20)
                .zipCode("00-001")
                .phoneNumber(555444666)
                .user(persistedUser)
                .build();

        entityManager.persistAndFlush(userData);
    }

    @Test
    void givenExistingUserId_whenFindUserId_thenReturnUserData(){
        //when
        Optional<UserData> optionalUserData = userDataRepository.findByUser_UserId(userData.getUser().getUserId());
        //then
        assertEquals(userData, optionalUserData.get());
    }

    @Test
    void givenNonExistentUserId_whenFindUserId_thenEmptyOptional(){
        //when
        Optional<UserData> optionalUserData = userDataRepository.findByUser_UserId(9999L);
        //then
        assertFalse(optionalUserData.isPresent());
    }

    @Test
    void givenInvalidUserId_whenFindUserId_thenEmptyOptional(){
        //when
        Optional<UserData> optionalUserData = userDataRepository.findByUser_UserId(-1L);
        //then
        assertFalse(optionalUserData.isPresent());
    }
}