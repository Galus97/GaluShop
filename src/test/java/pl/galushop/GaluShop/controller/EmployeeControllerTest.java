package pl.galushop.GaluShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import pl.galushop.GaluShop.configuration.SpringSecurity;
import pl.galushop.GaluShop.service.EmployeeService;
import pl.galushop.GaluShop.service.RegisterEmployeeService;

import static org.junit.jupiter.api.Assertions.*;

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


}