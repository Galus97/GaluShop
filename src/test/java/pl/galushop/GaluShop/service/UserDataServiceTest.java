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
import pl.galushop.GaluShop.model.User;
import pl.galushop.GaluShop.model.UserData;
import pl.galushop.GaluShop.exception.UserDataNotFoundException;
import pl.galushop.GaluShop.exception.UserNotFoundException;
import pl.galushop.GaluShop.repository.UserDataRepository;
import pl.galushop.GaluShop.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
    private UserDataRequest userDataRequest;

    @BeforeEach
    void setup() {
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

        userDataRequest = UserDataRequest.builder()
                .userDataId(null)
                .city("Warsaw")
                .street("Pulawska")
                .streetNumber(1)
                .apartmentNumber(1)
                .zipCode("00-001")
                .phoneNumber(666777888)
                .userId(1L)
                .build();
    }

    @Test
    void givenCorrectRequest_whenSaveUserData_thenReturnsUserDataResponse() {
        //given
        when(repository.save(any())).thenReturn(userData);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        //when
        UserDataResponse response = service.saveUserData(userDataRequest);
        //then
        assertNotNull(response);
        assertEquals(userData.getUserDataId(), response.userDataId());
        assertEquals(userData.getCity(), response.city());
        assertEquals(userData.getZipCode(), response.zipCode());
        verify(repository, times(1)).save(any(UserData.class));
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    void givenNullRequest_whenSaveUserData_thenThrowsException() {
        //then
        assertThrows(IllegalArgumentException.class, () -> service.saveUserData(null));
        verify(repository, times(0)).save(any(UserData.class));
        verify(userRepository, times(0)).findById(anyLong());
    }

    @Test
    void givenNotFoundUser_whenSaveUserData_thenThrowsException() {
        //given
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(UserNotFoundException.class, () -> service.saveUserData(userDataRequest));
        verify(userRepository, times(1)).findById(anyLong());
        verify(repository, times(0)).save(any(UserData.class));
    }

    @Test
    void givenExistingUserDataId_whenGetUserDataResponse_thenReturnsUserDataResponse() {
        //given
        when(repository.findById(anyLong())).thenReturn(Optional.of(userData));
        //when
        UserDataResponse response = service.getUserDataResponse(1L);
        //then
        assertNotNull(response);
        assertEquals(userData.getUserDataId(), response.userDataId());
        assertEquals(userData.getStreet(), response.street());
        assertEquals(userData.getPhoneNumber(), response.phoneNumber());
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void givenNonExistentUserData_whenGetUserDataResponse_thenThrowsException() {
        //given
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(UserDataNotFoundException.class, () -> service.getUserDataResponse(1L));
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void givenExistingUserId_whenGetUserDataByUserId_thenReturnsUserDataResponse() {
        //given
        when(repository.findByUser_UserId(anyLong())).thenReturn(Optional.of(userData));
        //when
        UserDataResponse response = service.getUserDataByUser(1L);
        //then
        assertNotNull(response);
        assertEquals(userData.getUserDataId(), response.userDataId());
        assertEquals(userData.getStreet(), response.street());
        assertEquals(userData.getPhoneNumber(), response.phoneNumber());
        verify(repository, times(1)).findByUser_UserId(anyLong());
    }

    @Test
    void givenNonExistentUserId_whenGetUserDataByUserId_thenThrowsException() {
        //given
        when(repository.findByUser_UserId(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(UserDataNotFoundException.class, () -> service.getUserDataByUser(1L));
        verify(repository, times(1)).findByUser_UserId(anyLong());
    }

    @Test
    void givenExistingUserDataId_whenDeleteUserData_thenDeletesUserData() {
        //given
        when(repository.findById(anyLong())).thenReturn(Optional.of(userData));
        //when
        service.deleteUserData(1L);
        //then
        verify(repository, times(1)).delete(any(UserData.class));
    }

    @Test
    void givenCorrectRequest_whenUpdateUserData_thenReturnsUserDataResponse() {
        //given
        userDataRequest.setUserDataId(1L);
        when(repository.findById(anyLong())).thenReturn(Optional.of(userData));
        when(repository.save(any(UserData.class))).thenReturn(userData);
        //when
        UserDataResponse userDataResponse = service.updateUserData(userDataRequest);
        //then
        assertNotNull(userDataResponse);
    }
}