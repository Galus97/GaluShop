package pl.galushop.GaluShop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderProductIdTest {
    private OrderProductId orderProductId1;
    private OrderProductId orderProductId2;

    @BeforeEach
    void setUp() {
        orderProductId1 = new OrderProductId(1L, 2L);
        orderProductId2 = new OrderProductId(1L, 2L);
    }

    @Test
    void shouldCreateOrderProductIdUsingConstructor() {
        assertThat(orderProductId1).isNotNull();
        assertThat(orderProductId1.getOrderId()).isEqualTo(1L);
        assertThat(orderProductId1.getProductId()).isEqualTo(2L);
    }

    @Test
    void shouldSetAndGetValuesCorrectly() {
        OrderProductId orderProductId = new OrderProductId(3L, 4L);

        assertThat(orderProductId.getOrderId()).isEqualTo(3L);
        assertThat(orderProductId.getProductId()).isEqualTo(4L);
    }

    @Test
    void shouldImplementEqualsCorrectly() {
        assertThat(orderProductId1).isEqualTo(orderProductId2);
        assertThat(orderProductId1.hashCode()).isEqualTo(orderProductId2.hashCode());
    }
}