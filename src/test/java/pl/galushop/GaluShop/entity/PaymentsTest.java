package pl.galushop.GaluShop.entity;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.galushop.GaluShop.component.OrderStatus;
import pl.galushop.GaluShop.component.PaymentStatus;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class PaymentsTest {
    private Payments payments;
    private Order order;
    private User user;
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        user = new User();
        user.setUserId(1L);

        order = Order.builder()
                .orderId(1L)
                .localDateTime(LocalDateTime.now())
                .status(OrderStatus.PLACED)
                .user(user)
                .orderProducts(Collections.emptyList())
                .build();

        payments = Payments.builder()
                .paymentId(1L)
                .totalAmount(100.0)
                .paymentStatus(PaymentStatus.PENDING)
                .order(order)
                .user(user)
                .build();
    }

    @Test
    void shouldCreatePaymentsUsingBuilder() {
        assertNotNull(payments);
        assertNotNull(payments.getTotalAmount());
        assertNotNull(payments.getPaymentStatus());
        assertNotNull(payments.getOrder());
        assertNotNull(payments.getUser());
    }
}