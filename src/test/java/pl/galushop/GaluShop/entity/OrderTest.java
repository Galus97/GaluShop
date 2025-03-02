package pl.galushop.GaluShop.entity;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.galushop.GaluShop.component.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTest {
    private static Validator validator;
    private Order order;
    private static final LocalDateTime FIXED_TIME = LocalDateTime.of(2025, 2, 27, 12, 0);

    @BeforeEach
    void setUp(){
        if(validator == null){
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
        }
        order = Order.builder()
                .orderProducts(new ArrayList<>())
                .user(new User())
                .status(OrderStatus.PROCESSED)
                .localDateTime(FIXED_TIME)
                .build();
    }

    @Test
    void shouldCreateOrderUsingBuilder(){
        //then
        assertThat(order).isNotNull();
        assertThat(order.getOrderProducts()).isNotNull();
        assertThat(order.getUser()).isNotNull();
        assertThat(order.getStatus().name()).isEqualTo("PROCESSED");
        assertThat(order.getLocalDateTime()).isEqualTo(FIXED_TIME);

    }
}