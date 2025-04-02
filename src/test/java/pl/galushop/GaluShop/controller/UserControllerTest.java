package pl.galushop.GaluShop.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.galushop.GaluShop.configuration.SpringSecurity;
import pl.galushop.GaluShop.dto.request.UserRequest;
import pl.galushop.GaluShop.dto.response.UserResponse;
import pl.galushop.GaluShop.exception.UserNotFoundException;
import pl.galushop.GaluShop.exception.ValidationException;
import pl.galushop.GaluShop.service.RegisterUserService;
import pl.galushop.GaluShop.service.UserService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(SpringSecurity.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService service;
    @MockBean
    private RegisterUserService registerUserService;
    private UserRequest request;
    private UserResponse response;

    @BeforeEach
    void setUp(){
        request = UserRequest.builder()
                .userId(1L)
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@mail.com")
                .password("newPassword")
                .build();
        response = new UserResponse(1L, "Jane", "Doe",
                "jane.doe@mail.com", true, "1111");
    }

    @Test
    void givenExistingId_whenShowUser_thenReturnsUser() throws Exception{
        //given
        when(service.getUserResponse(anyLong())).thenReturn(response);
        //then
        mockMvc.perform(get("/user/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("jane.doe@mail.com"))
                .andExpect(jsonPath("$.enabled").value(true))
                .andExpect(jsonPath("$.emailCode").value("1111"));
        verify(service, times(1)).getUserResponse(1L);
    }

    @Test
    void givenNonExistentId_whenShowUser_thenReturnsNotFound() throws Exception{
        //given
        when(service.getUserResponse(anyLong())).thenThrow(UserNotFoundException.class);
        //then
        mockMvc.perform(get("/user/999"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(service, times(1)).getUserResponse(999L);
    }

    @Test
    void givenNonInvalidId_whenShowUser_thenReturnsBadRequest() throws Exception{
        //given
        when(service.getUserResponse(anyLong())).thenThrow(IllegalArgumentException.class);
        //then
        mockMvc.perform(get("/user/-1"))
                .andExpect(status().isBadRequest());
        verify(service, times(1)).getUserResponse(-1L);
    }

    @Test
    void givenCorrectRequest_whenSaveUser_thenReturnsUser() throws Exception{
        //given
        when(registerUserService.saveNewUser(any(UserRequest.class))).thenReturn(response);
        //then
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/user/1"))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.email").value("jane.doe@mail.com"));
        verify(registerUserService, times(1)).saveNewUser(request);
    }

    @Test
    void givenInCorrectRequest_whenSaveUser_thenReturnsBadRequest() throws Exception{
        //given
        when(registerUserService.saveNewUser(any(UserRequest.class)))
                .thenThrow(new ValidationException(Collections.singletonList("Invalid email format")));
        //then
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0]").value("Invalid email format"));
        verify(registerUserService, times(1)).saveNewUser(request);
    }
}