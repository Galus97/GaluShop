package pl.galushop.GaluShop.dto.response;

import org.junit.jupiter.api.Test;
import pl.galushop.GaluShop.component.PaymentStatus;
import pl.galushop.GaluShop.model.Order;
import pl.galushop.GaluShop.model.Payment;
import pl.galushop.GaluShop.model.User;

import static org.junit.jupiter.api.Assertions.*;

class PaymentResponseTest {

    @Test
    void givenPayment_whenFromEntity_thenReturnsCorrectResponse(){
        //given
        Order order = new Order();
        order.setOrderId(1L);
        User user = new User();
        user.setUserId(1L);

        Payment payment = Payment.builder()
                .paymentId(1L)
                .totalAmount(10d)
                .paymentStatus(PaymentStatus.NEW)
                .order(order)
                .user(user)
                .build();
        //when
        PaymentResponse response = PaymentResponse.fromEntity(payment);
        //then
        assertEquals(1L, response.paymentId());
        assertEquals(10.0, response.totalAmount());
        assertEquals(PaymentStatus.NEW, response.paymentStatus());
        assertEquals(1L, response.orderId());
        assertEquals(1L, response.userId());
    }
}