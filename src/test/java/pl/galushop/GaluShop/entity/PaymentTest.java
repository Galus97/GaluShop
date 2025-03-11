package pl.galushop.GaluShop.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.galushop.GaluShop.component.OrderStatus;
import pl.galushop.GaluShop.component.PaymentStatus;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PaymentTest {
    private Payment payment;
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

        payment = Payment.builder()
                .paymentId(1L)
                .totalAmount(100.0)
                .paymentStatus(PaymentStatus.PENDING)
                .order(order)
                .user(user)
                .build();
    }

    @Test
    void shouldCreatePaymentsUsingBuilder() {
        assertNotNull(payment);
        assertNotNull(payment.getTotalAmount());
        assertNotNull(payment.getPaymentStatus());
        assertNotNull(payment.getOrder());
        assertNotNull(payment.getUser());
    }

    @Test
    void shouldCreatePaymentsWithNoArgsConstructor() {
        Payment emptyPayment = new Payment();
        assertThat(emptyPayment).isNotNull();
        assertThat(emptyPayment.getPaymentId()).isNull();
        assertThat(emptyPayment.getTotalAmount()).isNull();
        assertThat(emptyPayment.getPaymentStatus()).isNull();
        assertThat(emptyPayment.getOrder()).isNull();
        assertThat(emptyPayment.getUser()).isNull();
    }

    @Test
    void shouldValidateTotalAmountMinimumConstraint() {
        Payment invalidPayment = Payment.builder()
                .totalAmount(0.5)
                .paymentStatus(PaymentStatus.PENDING)
                .order(order)
                .user(user)
                .build();

        Set<ConstraintViolation<Payment>> violations = validator.validate(invalidPayment);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void shouldValidateNotNullPaymentStatus() {
        Payment invalidPayment = Payment.builder()
                .totalAmount(100.0)
                .paymentStatus(null)
                .order(order)
                .user(user)
                .build();

        Set<ConstraintViolation<Payment>> violations = validator.validate(invalidPayment);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void shouldUseLombokBuilderCorrectly() {
        assertThat(payment).satisfies(p -> {
            assertThat(p.getPaymentId()).isEqualTo(1L);
            assertThat(p.getTotalAmount()).isEqualTo(100.0);
            assertThat(p.getPaymentStatus()).isEqualTo(PaymentStatus.PENDING);
            assertThat(p.getOrder()).isEqualTo(order);
            assertThat(p.getUser()).isEqualTo(user);
        });
    }

    @Test
    void shouldHandleEqualityCorrectly() {
        Payment samePayment = Payment.builder()
                .paymentId(1L)
                .totalAmount(100.0)
                .paymentStatus(PaymentStatus.PENDING)
                .order(order)
                .user(user)
                .build();

        Payment differentPayment = Payment.builder()
                .paymentId(2L)
                .totalAmount(200.0)
                .paymentStatus(PaymentStatus.NEW)
                .order(new Order())
                .user(new User())
                .build();

        assertThat(payment)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(samePayment);

        assertThat(payment)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isNotEqualTo(differentPayment);
    }
}