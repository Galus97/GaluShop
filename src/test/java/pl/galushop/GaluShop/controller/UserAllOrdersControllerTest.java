package pl.galushop.GaluShop.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.galushop.GaluShop.component.OrderStatus;
import pl.galushop.GaluShop.configuration.SpringSecurity;
import pl.galushop.GaluShop.controller.all.UserAllOrdersController;
import pl.galushop.GaluShop.dto.response.OrderProductResponse;
import pl.galushop.GaluShop.dto.response.OrderResponse;
import pl.galushop.GaluShop.service.OrderService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
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

@WebMvcTest(UserAllOrdersController.class)
@Import(SpringSecurity.class)
@AutoConfigureMockMvc(addFilters = false)
class UserAllOrdersControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    OrderService orderService;

    @Test
    void givenExistingId_whenShowAllUserOrders_thenReturnsListOfOrders() throws Exception {
        //given
        List<OrderProductResponse> OrderProductResponseList = List.of(new OrderProductResponse(1L, 1L, 10));
        List<OrderResponse> orderResponseList = List.of(new OrderResponse(
                1L,
                LocalDateTime.of(2025, 3, 13, 12, 12, 12),
                OrderStatus.PROCESSED,
                1L,
                OrderProductResponseList));
        when(orderService.getAllOrdersByUser(anyLong())).thenReturn(orderResponseList);
        //then
        mockMvc.perform(get("/order/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].orderId", is(1)))
                .andExpect(jsonPath("$[0].localDateTime", is("2025-03-13T12:12:12")))
                .andExpect(jsonPath("$[0].status", is("PROCESSED")))
                .andExpect(jsonPath("$[0].userId", is(1)))
                .andExpect(jsonPath("$[0].products[0].orderId", is(1)))
                .andExpect(jsonPath("$[0].products[0].productId", is(1)))
                .andExpect(jsonPath("$[0].products[0].quantity", is(10)));
        verify(orderService, times(1)).getAllOrdersByUser(eq(1L));
    }

    @Test
    void givenNonExistent_whenShowAllUserOrders_thenReturnsEmptyList() throws Exception {
        //given
        when(orderService.getAllOrdersByUser(anyLong())).thenReturn(Collections.emptyList());
        //then
        mockMvc.perform(get("/order/user/999"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
        verify(orderService, times(1)).getAllOrdersByUser(eq(999L));
    }
}