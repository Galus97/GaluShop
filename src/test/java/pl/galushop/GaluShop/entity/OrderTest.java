package pl.galushop.GaluShop.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import pl.galushop.GaluShop.component.OrderStatus;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


class OrderTest {
    private Order order;
    private User user;
    private LocalDateTime fixedDateTime;
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        fixedDateTime = LocalDateTime.of(2023, 10, 10, 12, 30);
        user = new User();
        user.setUserId(1L);

        order = Order.builder()
                .orderId(1L)
                .localDateTime(fixedDateTime)
                .status(OrderStatus.PLACED)
                .user(user)
                .orderProducts(Collections.emptyList())
                .build();
    }

    @Nested
    class ConstructorTests {
        @Test
        void shouldCreateOrderWithAllArgsConstructor() {
            Order constructedOrder = new Order(
                    2L,
                    fixedDateTime,
                    OrderStatus.PAID,
                    user,
                    Collections.emptyList()
            );

            assertThat(constructedOrder)
                    .satisfies(o -> {
                        assertThat(o.getOrderId()).isEqualTo(2L);
                        assertThat(o.getLocalDateTime()).isEqualTo(fixedDateTime);
                        assertThat(o.getStatus()).isEqualTo(OrderStatus.PAID);
                        assertThat(o.getUser()).isEqualTo(user);
                        assertThat(o.getOrderProducts()).isEmpty();
                    });
        }

        @Test
        void shouldCreateOrderWithNoArgsConstructor() {
            Order emptyOrder = new Order();

            assertThat(emptyOrder)
                    .isNotNull()
                    .satisfies(o -> {
                        assertThat(o.getOrderId()).isNull();
                        assertThat(o.getLocalDateTime()).isNull();
                        assertThat(o.getStatus()).isNull();
                        assertThat(o.getUser()).isNull();
                        assertThat(o.getOrderProducts()).isNull();
                    });
        }
    }

    @Nested
    class BuilderTests {
        @Test
        void shouldCreateOrderUsingBuilder() {
            assertThat(order)
                    .satisfies(o -> {
                        assertThat(o.getOrderId()).isEqualTo(1L);
                        assertThat(o.getLocalDateTime()).isEqualTo(fixedDateTime);
                        assertThat(o.getStatus()).isEqualTo(OrderStatus.PLACED);
                        assertThat(o.getUser()).isEqualTo(user);
                        assertThat(o.getOrderProducts()).isEmpty();
                    });
        }

        @Test
        void shouldUseLombokSettersCorrectly() {
            order.setOrderId(2L);
            order.setStatus(OrderStatus.SENT);
            order.setLocalDateTime(null);
            order.setUser(null);

            assertThat(order)
                    .satisfies(o -> {
                        assertThat(o.getOrderId()).isEqualTo(2L);
                        assertThat(o.getStatus()).isEqualTo(OrderStatus.SENT);
                        assertThat(o.getLocalDateTime()).isNull();
                        assertThat(o.getUser()).isNull();
                    });
        }
    }

    @Nested
    class ValidationTests {
        @Test
        void shouldFailValidationWhenStatusIsNull() {
            Order invalidOrder = Order.builder()
                    .orderId(2L)
                    .localDateTime(fixedDateTime)
                    .user(user)
                    .build();

            Set<ConstraintViolation<Order>> violations = validator.validate(invalidOrder);
            assertThat(violations)
                    .extracting(ConstraintViolation::getMessage)
                    .contains("must not be null");
        }

        @Test
        void shouldFailValidationWhenUserIsNull() {
            Order invalidOrder = Order.builder()
                    .orderId(2L)
                    .localDateTime(fixedDateTime)
                    .status(OrderStatus.PAID)
                    .build();

            Set<ConstraintViolation<Order>> violations = validator.validate(invalidOrder);
            assertThat(violations)
                    .extracting(ConstraintViolation::getMessage)
                    .contains("must not be null");
        }
    }

    @Nested
    class UtilityMethodsTests {
        @Test
        void shouldImplementToStringCorrectly() {
            String toStringResult = order.toString();

            assertThat(toStringResult)
                    .contains("orderId=1")
                    .contains("status=PLACED")
                    .contains("user=" + user)
                    .contains("localDateTime=" + fixedDateTime);
        }
    }

    @Test
    void shouldHandleEqualityCorrectly() {
        Order sameOrder = Order.builder()
                .orderId(1L)
                .localDateTime(fixedDateTime)
                .status(OrderStatus.PLACED)
                .user(user)
                .orderProducts(Collections.emptyList())
                .build();

        Order differentOrder = Order.builder()
                .orderId(2L)
                .localDateTime(LocalDateTime.now())
                .status(OrderStatus.PAID)
                .user(new User())
                .build();

        assertThat(order).isEqualTo(sameOrder);
        //assertThat(order).isNotEqualTo(differentOrder);
    }
}