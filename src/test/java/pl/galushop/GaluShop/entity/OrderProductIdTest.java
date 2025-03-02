package pl.galushop.GaluShop.entity;

import org.junit.jupiter.api.BeforeEach;

class OrderProductIdTest {
    private OrderProductId orderProductId1;
    private OrderProductId orderProductId2;

    @BeforeEach
    void setUp() {
        orderProductId1 = new OrderProductId(1L, 2L);
        orderProductId2 = new OrderProductId(1L, 2L);
    }
}