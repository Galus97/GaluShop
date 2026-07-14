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

class OrderProductTest {
    private OrderProduct orderProduct;
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

        orderProduct = OrderProduct.builder()
                .id(new OrderProductId())
                .order(order)
                .product(product)
                .quantity(5)
                .build();
    }

    @Test
    void shouldCreateOrderProductUsingBuilder() {
        assertNotNull(orderProduct);
        assertNotNull(orderProduct.getOrder());
        assertNotNull(orderProduct.getProduct());
        assertNotNull(orderProduct.getId());
        assertEquals(5, orderProduct.getQuantity());
    }

    @Test
    void shouldCreateOrderProductWithAllArgsConstructor() {
        OrderProduct constructedOrderProduct = new OrderProduct(order, product, 10);

        assertThat(constructedOrderProduct)
                .satisfies(op -> {
                    assertThat(op.getOrder()).isEqualTo(order);
                    assertThat(op.getProduct()).isEqualTo(product);
                    assertThat(op.getQuantity()).isEqualTo(10);
                });
    }

    @Test
    void shouldCreateOrderProductWithNoArgsConstructor() {
        OrderProduct emptyOrderProduct = new OrderProduct();

        assertThat(emptyOrderProduct)
                .isNotNull()
                .satisfies(op -> {
                    assertThat(op.getOrder()).isNull();
                    assertThat(op.getProduct()).isNull();
                    assertThat(op.getQuantity()).isNull();
                });
    }

    @Test
    void shouldSetQuantityCorrectly() {
        orderProduct.setQuantity(8);
        assertThat(orderProduct.getQuantity()).isEqualTo(8);
    }

    @Test
    void shouldHandleEqualityCorrectly() {
        OrderProduct sameOrderProduct = OrderProduct.builder()
                .order(order)
                .product(product)
                .quantity(5)
                .build();

        OrderProduct differentOrderProduct = OrderProduct.builder()
                .order(order)
                .product(new Product())
                .quantity(3)
                .build();

        assertThat(orderProduct)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(sameOrderProduct);

        assertThat(orderProduct)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isNotEqualTo(differentOrderProduct);
    }

    @Test
    void shouldDetectInvalidQuantity(){
        orderProduct.setQuantity(-1);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        Set<ConstraintViolation<OrderProduct>> violations = validator.validate(orderProduct);

        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .contains("must be greater than or equal to 0");
    }
}
