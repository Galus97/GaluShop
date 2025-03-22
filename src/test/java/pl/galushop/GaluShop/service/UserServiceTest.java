package pl.galushop.GaluShop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

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


}