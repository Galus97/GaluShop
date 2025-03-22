package pl.galushop.GaluShop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.exception.UserNotFoundException;
import pl.galushop.GaluShop.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository repository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    MessageService messageService;
    @InjectMocks
    UserService service;

    private User user;

    @BeforeEach
    void setUp(){
        user = User.builder()
                .userId(1L)
                .firstName("John")
                .lastName("Smith")
                .email("john.smith@mail.com")
                .password("password")
                .enabled(true)
                .emailCode("1111")
                .build();
    }

    @Test
    void givenExistingId_whenGetUserEntity_thenReturnsUser(){
        //given
        when(repository.findById(any())).thenReturn(Optional.of(user));
        //when
        User userEntity = service.getUserEntity(1L);
        //then
        assertNotNull(userEntity);
        assertEquals(user, userEntity);
        verify(repository, (times(1))).findById(any());
    }

    @Test
    void givenNonExistentId_whenGetUserEntity_thenThrowsException(){
        //given
        when(repository.findById(any())).thenReturn(Optional.empty());
        //then
        assertThrows(UserNotFoundException.class, () -> service.getUserEntity(1L));
        verify(repository, (times(1))).findById(any());
    }

}