package pl.galushop.GaluShop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderItemIdTest {
    private OrderItemId orderItemId1;
    private OrderItemId orderItemId2;

    @BeforeEach
    void setUp() {
        orderItemId1 = new OrderItemId(1L, 2L);
        orderItemId2 = new OrderItemId(1L, 2L);
    }

    @Test
    void shouldCreateOrderProductIdUsingConstructor() {
        assertThat(orderItemId1).isNotNull();
        assertThat(orderItemId1.getOrderId()).isEqualTo(1L);
        assertThat(orderItemId1.getProductId()).isEqualTo(2L);
    }

    @Test
    void shouldSetAndGetValuesCorrectly() {
        OrderItemId orderItemId = new OrderItemId(3L, 4L);

        assertThat(orderItemId.getOrderId()).isEqualTo(3L);
        assertThat(orderItemId.getProductId()).isEqualTo(4L);
    }

    @Test
    void shouldImplementEqualsCorrectly() {
        assertThat(orderItemId1).isEqualTo(orderItemId2);
        assertThat(orderItemId1.hashCode()).isEqualTo(orderItemId2.hashCode());
    }
}