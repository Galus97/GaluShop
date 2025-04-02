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
import pl.galushop.GaluShop.dto.request.UserDataRequest;
import pl.galushop.GaluShop.dto.response.UserDataResponse;
import pl.galushop.GaluShop.service.UserDataService;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserDataController.class)
@Import(SpringSecurity.class)
@AutoConfigureMockMvc(addFilters = false)
class UserDataControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserDataService service;
    private UserDataResponse response;
    private UserDataRequest request;

    @BeforeEach
    void setUp(){
        request = UserDataRequest.builder()
                .userDataId(1L)
                .city("Warsaw")
                .street("Pulawska")
                .streetNumber(1)
                .apartmentNumber(1)
                .zipCode("00-001")
                .phoneNumber(666777888)
                .userId(1L)
                .build();
        response = new UserDataResponse(1L, "Warsaw", "Pulawska", 1, 1,
                "00-001", 666777888, 1L);
    }

    @Test
    void givenExistingId_whenShowUserDataByUserId_thenReturnsUserData() throws Exception{
        //given
        when(service.getUserDataByUserId(anyLong())).thenReturn(response);
        //then
        mockMvc.perform(get("/userData/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userDataId").value(1L))
                .andExpect(jsonPath("$.city").value("Warsaw"))
                .andExpect(jsonPath("$.street").value("Pulawska"))
                .andExpect(jsonPath("$.streetNumber").value(1))
                .andExpect(jsonPath("$.apartmentNumber").value(1))
                .andExpect(jsonPath("$.zipCode").value("00-001"))
                .andExpect(jsonPath("$.phoneNumber").value(666777888))
                .andExpect(jsonPath("$.userId").value(1L));
        verify(service, times(1)).getUserDataByUserId(1L);
    }

    @Test
    void givenExistingId_whenShowUserData_thenReturnsUserData() throws Exception{
        //given
        when(service.getUserDataResponse(anyLong())).thenReturn(response);
        //then
        mockMvc.perform(get("/userData/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userDataId").value(1L));
        verify(service, times(1)).getUserDataResponse(1L);
    }
}