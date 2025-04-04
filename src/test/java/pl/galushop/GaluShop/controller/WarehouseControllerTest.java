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
import pl.galushop.GaluShop.dto.request.WarehouseProductRequest;
import pl.galushop.GaluShop.dto.response.WarehouseProductResponse;
import pl.galushop.GaluShop.exception.WarehouseProductNotFoundException;
import pl.galushop.GaluShop.service.WarehouseProductService;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WarehouseController.class)
@Import(SpringSecurity.class)
@AutoConfigureMockMvc(addFilters = false)
class WarehouseControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private WarehouseProductService service;
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

    @Test
    void givenExistingId_whenShowWarehouseProduct_thenReturnsWarehouseProduct() throws Exception{
        //given
        when(service.getWarehouseProductResponse(anyLong())).thenReturn(response);
        //then
        mockMvc.perform(get("/warehouse/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.warehouseProductId").value(1L))
                .andExpect(jsonPath("$.productId").value(1L))
                .andExpect(jsonPath("$.quantity").value(10));
        verify(service, times(1)).getWarehouseProductResponse(1L);
    }

    @Test
    void givenNonExistentId_whenShowWarehouseProduct_thenReturnsNotFound() throws Exception{
        //given
        when(service.getWarehouseProductResponse(anyLong())).thenThrow(WarehouseProductNotFoundException.class);
        //then
        mockMvc.perform(get("/warehouse/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        verify(service, times(1)).getWarehouseProductResponse(999L);
    }
}