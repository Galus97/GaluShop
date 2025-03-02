package pl.galushop.GaluShop.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.galushop.GaluShop.component.OrderStatus;

import java.time.LocalDateTime;
import java.util.Collections;

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
        OrderProductId id = new OrderProductId();
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
                .id(id)
                .order(order)
                .product(product)
                .quantity(5)
                .build();

        id.setProductId(product.getProductId());
        id.setOrderId(order.getOrderId());
    }

    @Test
    void shouldCreateOrderProductUsingBuilder() {
        assertNotNull(orderProduct);
        assertNotNull(orderProduct.getOrder());
        assertNotNull(orderProduct.getProduct());
        assertNotNull(orderProduct.getId());
        assertEquals(5, orderProduct.getQuantity());
    }
}