package pl.galushop.GaluShop.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.galushop.GaluShop.configuration.SpringSecurity;
import pl.galushop.GaluShop.dto.response.WarehouseProductResponse;
import pl.galushop.GaluShop.service.WarehouseProductService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WarehouseAllProductController.class)
@Import(SpringSecurity.class)
@AutoConfigureMockMvc(addFilters = false)
class WarehouseAllProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private WarehouseProductService service;

    @Test
    void whenShowAllProductInWarehouse_thenReturnsWarehouseProductList() throws Exception{
        //given
        WarehouseProductResponse response = new WarehouseProductResponse(1L, 1L, 10);
        List<WarehouseProductResponse> responseList = Arrays.asList(response);
        when(service.getAllProductInWarehouse()).thenReturn(responseList);
        //then
        mockMvc.perform(get("/warehouse/allProduct"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].warehouseProductId").value(1L))
                .andExpect(jsonPath("$[0].productId").value(1L))
                .andExpect(jsonPath("$[0].quantity").value(10));
        verify(service, times(1)).getAllProductInWarehouse();
    }
}