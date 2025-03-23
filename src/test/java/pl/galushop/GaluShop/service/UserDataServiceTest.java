package pl.galushop.GaluShop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.dto.request.UserDataRequest;
import pl.galushop.GaluShop.dto.response.UserDataResponse;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.entity.UserData;
import pl.galushop.GaluShop.repository.UserDataRepository;
import pl.galushop.GaluShop.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
                .phoneNumber(666777888)
                .user(user)
                .build();

    }

    @Test
    void givenCorrectRequest_whenSaveUserData_thenReturnsUserDataResponse(){
        //given
        UserDataRequest userDataRequest = new UserDataRequest();
        userDataRequest.setUserId(null);
        userDataRequest.setCity("Warsaw");
        userDataRequest.setStreet("Pulawska");
        userDataRequest.setStreetNumber(1);
        userDataRequest.setApartmentNumber(1);
        userDataRequest.setZipCode("00-001");
        userDataRequest.setPhoneNumber(666777888);
        userDataRequest.setUserId(1L);

        when(repository.save(any())).thenReturn(userData);
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        //when
        UserDataResponse response = service.saveUserData(userDataRequest);
        //then
        assertNotNull(response);
        assertEquals(userData.getUserDataId(), response.userDataId());
        assertEquals(userData.getCity(), response.city());
        assertEquals(userData.getZipCode(), response.zipCode());
        verify(repository, times(1)).save(any());
        verify(userRepository, times(1)).findById(any());
    }
}