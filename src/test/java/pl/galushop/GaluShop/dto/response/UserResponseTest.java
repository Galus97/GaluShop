package pl.galushop.GaluShop.dto.response;

import org.junit.jupiter.api.Test;
import pl.galushop.GaluShop.entity.User;

import static org.junit.jupiter.api.Assertions.*;

class UserResponseTest {

    @Test
    void givenUser_whenFromEntity_thenReturnsUserResponse(){
        //given
        User user = User.builder()
                .userId(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@mail.com")
                .password("password")
                .enabled(true)
                .emailCode("1111")
                .build();
        //when
        UserResponse response = UserResponse.fromEntity(user);
        //then
        assertEquals(1L, response.userId());
        assertEquals("John", response.firstName());
        assertEquals("Doe", response.lastName());
        assertEquals("john.doe@mail.com", response.email());
        assertEquals(true, response.enabled());
        assertEquals("1111", response.emailCode());
    }
}