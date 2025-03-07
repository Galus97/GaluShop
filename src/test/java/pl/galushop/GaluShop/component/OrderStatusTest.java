package pl.galushop.GaluShop.component;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderStatusTest {

    @Test
    void shouldContainAllOrderLifecycleStatuses() {
        OrderStatus[] values = OrderStatus.values();

        assertThat(values)
                .containsExactly(
                        OrderStatus.PLACED,
                        OrderStatus.PAID,
                        OrderStatus.PROCESSED,
                        OrderStatus.SENT
                );
    }
}