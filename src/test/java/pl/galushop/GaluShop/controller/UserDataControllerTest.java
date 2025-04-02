package pl.galushop.GaluShop.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import pl.galushop.GaluShop.configuration.SpringSecurity;

@WebMvcTest(UserDataController.class)
@Import(SpringSecurity.class)
@AutoConfigureMockMvc(addFilters = false)
class UserDataControllerTest {

}