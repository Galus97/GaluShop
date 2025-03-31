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
import pl.galushop.GaluShop.dto.response.OrderProductResponse;
import pl.galushop.GaluShop.service.OrderProductService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderProductController.class)
@Import(SpringSecurity.class)
@AutoConfigureMockMvc(addFilters = false)
class OrderProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    OrderProductService orderProductService;

    @Test
    void givenExistingId_whenShowProductInOrder_thenReturnsOrderProductResponseList() throws Exception{
        //given
        OrderProductResponse orderProductResponse = new OrderProductResponse(1L, 1L, 15);
        List<OrderProductResponse> responseList = Arrays.asList(orderProductResponse);
        when(orderProductService.getOrderProductsByOrderId(anyLong())).thenReturn(responseList);
        //then
        mockMvc.perform(get("/order/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].orderId").value(1L))
                .andExpect(jsonPath("$[0].productId").value(1L))
                .andExpect(jsonPath("$[0].quantity").value(15));
        verify(orderProductService, times(1)).getOrderProductsByOrderId(1L);
    }

    @Test
    void givenInvalidId_whenShowProductInOrder_thenReturnsBadRequest() throws Exception{
        //given
        when(orderProductService.getOrderProductsByOrderId(anyLong())).thenThrow(IllegalArgumentException.class);
        //then
        mockMvc.perform(get("/order/products/-99"))
                .andExpect(status().isBadRequest());
        verify(orderProductService, times(1)).getOrderProductsByOrderId(anyLong());
    }
}