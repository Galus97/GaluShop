package pl.galushop.GaluShop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.entity.UserData;
import pl.galushop.GaluShop.repository.UserDataRepository;
import pl.galushop.GaluShop.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserDataServiceTest {

    @Mock
    UserDataRepository repository;
    @Mock
    UserRepository userRepository;
    @Mock
    MessageService messageService;
    @InjectMocks
    UserDataService service;
    private UserData userData;
    private User user;

    @BeforeEach
    void setup(){
        user = User.builder()
                .userId(1L)
                .firstName("John")
                .lastName("Smith")
                .email("john.smith@mail.com")
                .password("password")
                .enabled(true)
                .emailCode("1111")
                .build();

        userData = UserData.builder()
                .userDataId(1L)
                .city("Warsaw")
                .street("Pulawska")
                .streetNumber(1)
                .apartmentNumber(1)
                .zipCode("00-001")
                .phoneNumber(1)
                .user(user)
                .build();

    }
}