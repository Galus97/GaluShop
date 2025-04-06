package pl.galushop.GaluShop.dto.response;

import org.junit.jupiter.api.Test;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.entity.UserData;

import static org.junit.jupiter.api.Assertions.*;

class UserDataResponseTest {

    @Test
    void givenUserData_whenFromEntity_thenReturnsCorrectResponse(){
        //given
        User user = new User();
        user.setUserId(1L);

        UserData userData = UserData.builder()
                .userDataId(1L)
                .city("Warsaw")
                .street("Pulawska")
                .streetNumber(10)
                .apartmentNumber(20)
                .zipCode("00-001")
                .phoneNumber(666777888)
                .user(user)
                .build();
        //when
        UserDataResponse response = UserDataResponse.fromEntity(userData);
        //then
        assertEquals(1L, response.userDataId());
        assertEquals("Warsaw", response.city());
        assertEquals("Pulawska", response.street());
        assertEquals(10, response.streetNumber());
        assertEquals(20, response.apartmentNumber());
        assertEquals("00-001", response.zipCode());
        assertEquals(666777888, response.phoneNumber());
        assertEquals(1L, response.userDataId());
    }
}