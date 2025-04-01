package pl.galushop.GaluShop.controller;

import org.junit.jupiter.api.BeforeEach;
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
import pl.galushop.GaluShop.dto.request.PaymentRequest;
import pl.galushop.GaluShop.dto.response.PaymentResponse;
import pl.galushop.GaluShop.exception.PaymentNotFoundException;
import pl.galushop.GaluShop.service.PaymentService;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentsController.class)
@Import(SpringSecurity.class)
@AutoConfigureMockMvc(addFilters = false)
class PaymentsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    PaymentService service;
    private PaymentResponse response;
    private PaymentRequest request;

    @BeforeEach
    void setUp(){
        response = new PaymentResponse(1L, 125.5, PaymentStatus.NEW, 1L, 1L);
        request = PaymentRequest.builder()
                .paymentId(1L)
                .totalAmount(125.5)
                .paymentStatus(PaymentStatus.NEW)
                .orderId(1L)
                .userId(1L)
                .build();
    }

    @Test
    void givenExistingId_whenShowPayment_thenReturnsPayment() throws Exception{
        //given
        when(service.getPaymentResponse(anyLong())).thenReturn(response);
        //then
        mockMvc.perform(get("/payments/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.paymentId").value(1L))
                .andExpect(jsonPath("$.totalAmount").value(125.5))
                .andExpect(jsonPath("$.paymentStatus").value("NEW"))
                .andExpect(jsonPath("$.orderId").value(1L))
                .andExpect(jsonPath("$.userId").value(1L));
        verify(service, times(1)).getPaymentResponse(1L);
    }

    @Test
    void givenNonExistentId_whenShowPayment_thenReturnsNotFound() throws Exception{
        //given
        when(service.getPaymentResponse(anyLong())).thenThrow(PaymentNotFoundException.class);
        //then
        mockMvc.perform(get("/payments/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        verify(service, times(1)).getPaymentResponse(999L);
    }

    @Test
    void givenInvalidId_whenShowPayment_thenReturnsBadRequest() throws Exception{
        //given
        when(service.getPaymentResponse(anyLong())).thenThrow(IllegalArgumentException.class);
        //then
        mockMvc.perform(get("/payments/-1"))
                .andExpect(status().isBadRequest());
        verify(service, times(1)).getPaymentResponse(-1L);
    }
}