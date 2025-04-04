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
import pl.galushop.GaluShop.dto.request.WarehouseProductRequest;
import pl.galushop.GaluShop.dto.response.WarehouseProductResponse;
import pl.galushop.GaluShop.service.WarehouseProductService;

@WebMvcTest(WarehouseController.class)
@Import(SpringSecurity.class)
@AutoConfigureMockMvc(addFilters = false)
class WarehouseControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private WarehouseProductService warehouseProductService;
    private WarehouseProductResponse response;
    private WarehouseProductRequest request;

    @BeforeEach
    void setUp(){
        response = new WarehouseProductResponse(1L, 1L, 10);
        request = WarehouseProductRequest.builder()
                .warehouseProductId(1L)
                .productId(1L)
                .quantity(10)
                .build();
    }
}