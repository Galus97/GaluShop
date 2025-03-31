package pl.galushop.GaluShop.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.galushop.GaluShop.component.PaymentStatus;
import pl.galushop.GaluShop.configuration.SpringSecurity;
import pl.galushop.GaluShop.dto.response.PaymentResponse;
import pl.galushop.GaluShop.service.PaymentService;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentOrderController.class)
@Import(SpringSecurity.class)
@AutoConfigureMockMvc(addFilters = false)
class PaymentOrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    PaymentService service;

    @Test
    void givenExistingId_whenShowOrderPayment_thenReturnsPayment() throws Exception{
        //given
        PaymentResponse response = new PaymentResponse(1L, 15.5, PaymentStatus.NEW, 1L, 1L);
        when(service.getPaymentResponseByOrderId(anyLong())).thenReturn(response);
        //then
        mockMvc.perform(get("/payment/order/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.paymentId").value(1L))
                .andExpect(jsonPath("$.totalAmount").value(15.5))
                .andExpect(jsonPath("$.paymentStatus").value("NEW"))
                .andExpect(jsonPath("$.orderId").value(1L))
                .andExpect(jsonPath("$.userId").value(1L));
        verify(service, times(1)).getPaymentResponseByOrderId(1L);
    }
}