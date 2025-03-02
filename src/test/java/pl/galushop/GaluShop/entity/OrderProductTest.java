package pl.galushop.GaluShop.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.galushop.GaluShop.component.OrderStatus;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderProductTest {
    private OrderProduct orderProduct;
    private Order order;
    private Product product;
    private User user;

    private LocalDateTime fixedDateTime;

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
}
