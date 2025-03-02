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
    }
}