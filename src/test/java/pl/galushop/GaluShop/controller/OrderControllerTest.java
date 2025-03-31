package pl.galushop.GaluShop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.galushop.GaluShop.component.OrderStatus;
import pl.galushop.GaluShop.configuration.SpringSecurity;
import pl.galushop.GaluShop.dto.request.OrderRequest;
import pl.galushop.GaluShop.dto.request.ProductQuantityRequest;
import pl.galushop.GaluShop.dto.response.OrderProductResponse;
import pl.galushop.GaluShop.dto.response.OrderResponse;
import pl.galushop.GaluShop.exception.OrderNotFoundException;
import pl.galushop.GaluShop.service.OrderService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@Import(SpringSecurity.class)
@AutoConfigureMockMvc(addFilters = false)
class OrderControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    OrderService service;
    private OrderRequest request;
    private OrderResponse response;

    @BeforeEach
    void setUp(){
        ProductQuantityRequest productQuantityRequest = new ProductQuantityRequest();
        productQuantityRequest.setProductId(1L);
        productQuantityRequest.setQuantity(10);
        List<ProductQuantityRequest> productQuantityRequests = Arrays.asList(productQuantityRequest);

        request = OrderRequest.builder()
                .orderId(1L)
                .userId(1L)
                .localDateTime(LocalDateTime.of(2025, 3, 30, 12, 12))
                .orderStatus(OrderStatus.PROCESSED)
                .productQuantityRequests(productQuantityRequests)
                .build();

        List<OrderProductResponse> products = Arrays.asList(new OrderProductResponse(1L, 1L, 10));
        response = new OrderResponse(
                1L,
                LocalDateTime.of(2025, 3, 30, 12, 12),
                OrderStatus.PROCESSED,
                1L,
                products);

    }

    @Test
    void givenExitingId_whenShowOrder_thenReturnsOrder() throws Exception{
        //given
        when(service.getOrderResponse(anyLong())).thenReturn(response);
        //then
        mockMvc.perform(get("/order/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(1L))
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.localDateTime").value("2025-03-30T12:12:00"))
                .andExpect(jsonPath("$.status").value("PROCESSED"))
                .andExpect(jsonPath("$.products[0].orderId").value(1L))
                .andExpect(jsonPath("$.products[0].productId").value(1L))
                .andExpect(jsonPath("$.products[0].quantity").value(10));
        verify(service, times(1)).getOrderResponse(eq(1L));
    }

    @Test
    void givenNonExistentId_whenShowOrder_thenReturnsNotFound() throws Exception{
        //given
        when(service.getOrderResponse(anyLong())).thenThrow(OrderNotFoundException.class);
        //then
        mockMvc.perform(get("/order/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(service, times(1)).getOrderResponse(eq(999L));
    }

    @Test
    void givenInvalidId_whenShowOrder_thenReturnsBadRequest() throws Exception{
        //given
        when(service.getOrderResponse(anyLong())).thenThrow(IllegalArgumentException.class);
        //then
        mockMvc.perform(get("/order/-1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(service, times(1)).getOrderResponse(eq(-1L));
    }

    @Test
    void givenCorrectRequest_whenShowOrder_thenReturnsOrder() throws Exception{
        //given
        when(service.saveOrder(any(OrderRequest.class))).thenReturn(response);
        //then
        mockMvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/order/1"))
                .andExpect(jsonPath("$.orderId").value(1));
        verify(service, times(1)).saveOrder(request);
    }

    @Test
    void givenCorrectRequest_whenUpdateOrder_thenReturnsOrder() throws Exception{
        //given
        when(service.updateOrder(any(OrderRequest.class))).thenReturn(response);
        //then
        mockMvc.perform(put("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(1));
        verify(service, times(1)).updateOrder(request);
    }

    @Test
    void givenExistingId_whenDeleteOrder_thenDeletesOrder() throws Exception{
        //given
        doNothing().when(service).deleteOrder(anyLong());
        //then
        mockMvc.perform(delete("/order/1"))
                .andExpect(status().isNoContent());
        verify(service, times(1)).deleteOrder(1L);
    }
}