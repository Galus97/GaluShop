package pl.galushop.GaluShop.entity;

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
        OrderProductId orderProductId = new OrderProductId();
        orderProductId.setOrderId(3L);
        orderProductId.setProductId(4L);

        assertThat(orderProductId.getOrderId()).isEqualTo(3L);
        assertThat(orderProductId.getProductId()).isEqualTo(4L);
    }
}