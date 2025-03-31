package pl.galushop.GaluShop.controller;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import pl.galushop.GaluShop.component.PaymentStatus;
import pl.galushop.GaluShop.configuration.SpringSecurity;
import pl.galushop.GaluShop.dto.request.PaymentRequest;
import pl.galushop.GaluShop.dto.response.PaymentResponse;
import pl.galushop.GaluShop.service.PaymentService;

@WebMvcTest(PaymentsController.class)
@Import(SpringSecurity.class)
@AutoConfigureMockMvc(addFilters = false)
class PaymentsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    PaymentService paymentService;
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


}