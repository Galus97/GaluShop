package pl.galushop.GaluShop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WarehouseUpdateQuantityController.class)
@Import(SpringSecurity.class)
@AutoConfigureMockMvc(addFilters = false)
class WarehouseUpdateQuantityControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private WarehouseProductService service;

    @Test
    void givenExistingId_whenUpdateQuantity_thenReturnsWarehouseProduct() throws Exception{
        //given
        WarehouseProductResponse response = new WarehouseProductResponse(10L, 5L, 10);
        when(service.updateQuantityByProductId(anyLong(), anyInt())).thenReturn(response);
        //then
        mockMvc.perform(put("/warehouse/10/5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.warehouseProductId").value(10L))
                .andExpect(jsonPath("$.productId").value(5L))
                .andExpect(jsonPath("$.quantity").value(10));
        verify(service, times(1)).updateQuantityByProductId(10L, 5);
    }

    @Test
    void givenInvalidQuantity_whenUpdateQuantity_thenReturnsBadRequest() throws Exception{
        //given
        when(service.updateQuantityByProductId(anyLong(), anyInt())).thenThrow(IllegalArgumentException.class);
        //then
        mockMvc.perform(put("/warehouse/10/-5"))
                .andExpect(status().isBadRequest());
        verify(service, times(1)).updateQuantityByProductId(10L, -5);
    }
}