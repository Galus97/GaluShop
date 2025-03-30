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
import pl.galushop.GaluShop.dto.request.EmployeeRequest;
import pl.galushop.GaluShop.dto.response.EmployeeResponse;
import pl.galushop.GaluShop.exception.EmployeeNotFoundException;
import pl.galushop.GaluShop.exception.ValidationException;
import pl.galushop.GaluShop.service.EmployeeService;
import pl.galushop.GaluShop.service.RegisterEmployeeService;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
@Import(SpringSecurity.class)
@AutoConfigureMockMvc(addFilters = false)
class EmployeeControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    EmployeeService employeeService;
    @MockBean
    RegisterEmployeeService registerEmployeeService;
    private EmployeeResponse employeeResponse;
    private EmployeeRequest employeeRequest;

    @BeforeEach
    void setUp(){
        employeeResponse = new EmployeeResponse(
                1L,
                "John",
                "Doe",
                "john.doe@mail.com",
                true,
                "1111");
        employeeRequest = EmployeeRequest.builder()
                .employeeId(null)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@mail.com")
                .password("password123")
                .build();
    }

    @Test
    void givenExistingId_whenShowEmployee_thenReturnsEmployee() throws Exception{
        //given
        when(employeeService.getEmployeeResponse(anyLong())).thenReturn(employeeResponse);
        //then
        mockMvc.perform(get("/employee/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.employeeId", is(1)))
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Doe")))
                .andExpect(jsonPath("$.email", is("john.doe@mail.com")))
                .andExpect(jsonPath("$.enabled", is(true)))
                .andExpect(jsonPath("$.emailCode", is("1111")));
        verify(employeeService, times(1)).getEmployeeResponse(eq(1L));
    }

    @Test
    void givenNonExistentId_whenShowEmployee_thenReturnsNotFound() throws Exception{
        //given
        when(employeeService.getEmployeeResponse(anyLong())).thenThrow(EmployeeNotFoundException.class);
        //then
        mockMvc.perform(get("/employee/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        verify(employeeService, times(1)).getEmployeeResponse(eq(999L));
    }

    @Test
    void givenInvalidId_whenShowEmployee_thenReturnsBadRequest() throws Exception{
        //given
        when(employeeService.getEmployeeResponse(anyLong())).thenThrow(IllegalArgumentException.class);
        //then
        mockMvc.perform(get("/employee/-1"))
                .andExpect(status().isBadRequest());
        verify(employeeService, times(1)).getEmployeeResponse(-1L);
    }

    @Test
    void givenCorrectRequest_whenSaveEmployee_thenReturnsEmployee() throws Exception{
        //given
        when(registerEmployeeService.saveNewEmployee(any(EmployeeRequest.class))).thenReturn(employeeResponse);

        //then
        mockMvc.perform(post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/employee/1"))
                .andExpect(jsonPath("$.employeeId").value(1));
        verify(registerEmployeeService, times(1)).saveNewEmployee(any(EmployeeRequest.class));
    }

    @Test
    void givenIncorrectRequest_whenSaveEmployee_thenReturnsBadRequest() throws Exception{
        //given
        when(registerEmployeeService.saveNewEmployee(any(EmployeeRequest.class)))
                .thenThrow(new ValidationException(Collections.singletonList("Invalid email format")));

        //then
        mockMvc.perform(post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0]").value("Invalid email format"));
        verify(registerEmployeeService, times(1)).saveNewEmployee(any(EmployeeRequest.class));
    }

    @Test
    void givenCorrectRequest_whenUpdateEmployee_thenReturnsEmployee() throws Exception{
        //given
        when(employeeService.updateEmployee(any(EmployeeRequest.class))).thenReturn(employeeResponse);
        //then
        mockMvc.perform(put("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").value(1));
        verify(employeeService, times(1)).updateEmployee(any(EmployeeRequest.class));
    }

    @Test
    void givenExistingId_whenDeleteEmployee_thenDeletesEmployee() throws Exception{
        //then
        mockMvc.perform(delete("/employee/1"))
                .andExpect(status().isNoContent());
        verify(employeeService, times(1)).deleteEmployee(1L);
    }

//    @Test
//    void givenInvalidId_whenDeleteEmployee_thenDeletesEmployee() throws Exception{
//        //then
//        mockMvc.perform(delete("/employee/-1"))
//                .andExpect(status().isNoContent());
//        verify(employeeService, times(1)).deleteEmployee(-1L);
//    }
}