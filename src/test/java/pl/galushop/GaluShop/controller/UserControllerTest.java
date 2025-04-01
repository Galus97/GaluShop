package pl.galushop.GaluShop.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import pl.galushop.GaluShop.configuration.SpringSecurity;
import pl.galushop.GaluShop.dto.request.UserRequest;
import pl.galushop.GaluShop.dto.response.UserResponse;
import pl.galushop.GaluShop.service.RegisterUserService;
import pl.galushop.GaluShop.service.UserService;

@WebMvcTest(UserController.class)
@Import(SpringSecurity.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private RegisterUserService service;
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
}