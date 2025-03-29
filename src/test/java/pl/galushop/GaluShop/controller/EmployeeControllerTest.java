package pl.galushop.GaluShop.controller;

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
import pl.galushop.GaluShop.dto.response.EmployeeResponse;
import pl.galushop.GaluShop.exception.EmployeeNotFoundException;
import pl.galushop.GaluShop.service.EmployeeService;
import pl.galushop.GaluShop.service.RegisterEmployeeService;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
@Import(SpringSecurity.class)
@AutoConfigureMockMvc(addFilters = false)
class EmployeeControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    EmployeeService employeeService;
    @MockBean
    RegisterEmployeeService registerEmployeeService;
    private EmployeeResponse employeeResponse;

    @BeforeEach
    void setUp(){
        employeeResponse = new EmployeeResponse(
                1L,
                "John",
                "Doe",
                "john.doe@mail.com",
                true,
                "1111");
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
    void givenNonExistentId_whenShowEmployee_thenReturnsBadRequest() throws Exception{
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
}