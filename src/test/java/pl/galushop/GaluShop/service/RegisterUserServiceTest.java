package pl.galushop.GaluShop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.galushop.GaluShop.util.RegisterValidator;
import pl.galushop.GaluShop.dto.request.UserRequest;
import pl.galushop.GaluShop.dto.response.UserResponse;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterUserServiceTest {
    @Mock
    UserRepository repository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    RegisterValidator registerValidator;
    @Mock
    EmailService emailService;
    @InjectMocks
    RegisterUserService service;

    @Test
    void givenCorrectRequest_whenSaveNewUser_thenReturnsUserResponse() throws Exception{
        //given
        UserRequest userRequest = UserRequest.builder()
                .userId(null)
                .firstName("John")
                .lastName("Smith")
                .email("john.smith@mail.com")
                .password("password")
                .build();
        User user = User.builder()
                .userId(1L)
                .firstName("John")
                .lastName("Smith")
                .email("john.smith@mail.com")
                .password("password")
                .enabled(true)
                .emailCode("1111")
                .build();
        when(repository.save(any(User.class))).thenReturn(user);
        //when
        UserResponse response = service.saveNewUser(userRequest);
        //then
        assertNotNull(response);
        assertEquals("John", response.firstName());
        assertEquals("Smith", response.lastName());
        assertEquals("john.smith@mail.com", response.email());
        assertEquals("1111", response.emailCode());
        assertTrue(response.enabled());
        verify(repository, times(1)).save(any(User.class));
    }
}