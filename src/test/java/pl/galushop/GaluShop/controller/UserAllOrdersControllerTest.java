package pl.galushop.GaluShop.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import pl.galushop.GaluShop.component.OrderStatus;
import pl.galushop.GaluShop.configuration.SpringSecurity;
import pl.galushop.GaluShop.dto.OrderProductDto;
import pl.galushop.GaluShop.dto.response.OrderResponse;
import pl.galushop.GaluShop.service.OrderService;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
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
    void whenShowAllUserOrders_then() throws Exception{
        //given
        List<OrderProductDto> orderProductDtoList = List.of( new OrderProductDto(1L, 1L, 10));
        List<OrderResponse> orderResponseList = List.of(new OrderResponse(
                1L,
                LocalDateTime.of(2025, 3, 13, 12, 12, 12),
                OrderStatus.PROCESSED,
                1L,
                orderProductDtoList));
        when(orderService.getAllOrdersByUser(anyLong())).thenReturn(orderResponseList);
        //then
        mockMvc.perform(get("/order/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].orderId", is(1)))
                .andExpect(jsonPath("$[0].localDateTime", is("2025-03-13T12:12:12")))
                .andExpect(jsonPath("$[0].status", is("PROCESSED")))
                .andExpect(jsonPath("$[0].userId", is(1)))
                .andExpect(jsonPath("$[0].products[0].orderId", is(1)))
                .andExpect(jsonPath("$[0].products[0].productId", is(1)))
                .andExpect(jsonPath("$[0].products[0].quantity", is(10)));
    }
}