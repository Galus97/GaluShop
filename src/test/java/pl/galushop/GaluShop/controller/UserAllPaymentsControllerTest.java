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

@WebMvcTest(UserAllPaymentsController.class)
@Import(SpringSecurity.class)
@AutoConfigureMockMvc(addFilters = false)
class UserAllPaymentsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PaymentService service;

    @Test
    void givenExistingId_whenShowAllUserPayments_thenReturnsPaymentList() throws Exception{
        //given
        PaymentResponse response = new PaymentResponse(1L, 15d, PaymentStatus.NEW, 1L, 1L);
        List<PaymentResponse> responseList = Arrays.asList(response);
        when(service.getAllPaymentResponseByUserId(anyLong())).thenReturn(responseList);
        //then
        mockMvc.perform(get("/payments/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].paymentId").value(1L))
                .andExpect(jsonPath("$[0].totalAmount").value(15.0))
                .andExpect(jsonPath("$[0].paymentStatus").value("NEW"))
                .andExpect(jsonPath("$[0].orderId").value(1L))
                .andExpect(jsonPath("$[0].userId").value(1L));
        verify(service, times(1)).getAllPaymentResponseByUserId(1L);
    }
}