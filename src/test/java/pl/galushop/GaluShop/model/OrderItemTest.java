package pl.galushop.GaluShop.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.galushop.GaluShop.component.OrderStatus;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderItemTest {
    private OrderItem orderItem;
    private Order order;
    private Product product;
    private User user;

    private LocalDateTime fixedDateTime;
    private static Validator validator;

    @BeforeEach
    void setUp() {

        fixedDateTime = LocalDateTime.of(2023, 10, 10, 12, 30);
        order = Order.builder()
                .orderId(1L)
                .localDateTime(fixedDateTime)
                .status(OrderStatus.PLACED)
                .user(user)
                .orderProducts(Collections.emptyList())
                .build();

        product = Product.builder()
                .productId(2L)
                .productName("Sample Product")
                .description("Sample Description of the Product")
                .price(100.0)
                .category("Sample Category")
                .categoryId(1)
                .build();

        orderItem = OrderItem.builder()
                .id(new OrderItemId())
                .order(order)
                .product(product)
                .quantity(5)
                .build();
    }

    @Test
    void shouldCreateOrderProductUsingBuilder() {
        assertNotNull(orderItem);
        assertNotNull(orderItem.getOrder());
        assertNotNull(orderItem.getProduct());
        assertNotNull(orderItem.getId());
        assertEquals(5, orderItem.getQuantity());
    }

    @Test
    void shouldCreateOrderProductWithAllArgsConstructor() {
        OrderItem constructedOrderItem = new OrderItem(order, product, 10);

        assertThat(constructedOrderItem)
                .satisfies(op -> {
                    assertThat(op.getOrder()).isEqualTo(order);
                    assertThat(op.getProduct()).isEqualTo(product);
                    assertThat(op.getQuantity()).isEqualTo(10);
                });
    }

    @Test
    void shouldCreateOrderProductWithNoArgsConstructor() {
        OrderItem emptyOrderItem = new OrderItem();

        assertThat(emptyOrderItem)
                .isNotNull()
                .satisfies(op -> {
                    assertThat(op.getOrder()).isNull();
                    assertThat(op.getProduct()).isNull();
                    assertThat(op.getQuantity()).isNull();
                });
    }

    @Test
    void shouldSetQuantityCorrectly() {
        orderItem.setQuantity(8);
        assertThat(orderItem.getQuantity()).isEqualTo(8);
    }

    @Test
    void shouldHandleEqualityCorrectly() {
        OrderItem sameOrderItem = OrderItem.builder()
                .order(order)
                .product(product)
                .quantity(5)
                .build();

        OrderItem differentOrderItem = OrderItem.builder()
                .order(order)
                .product(new Product())
                .quantity(3)
                .build();

        assertThat(orderItem)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(sameOrderItem);

        assertThat(orderItem)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isNotEqualTo(differentOrderItem);
    }

    @Test
    void shouldDetectInvalidQuantity(){
        orderItem.setQuantity(-1);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        Set<ConstraintViolation<OrderItem>> violations = validator.validate(orderItem);

        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .contains("must be greater than or equal to 0");
    }
}
