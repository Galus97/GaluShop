package pl.galushop.GaluShop.component;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    void shouldHaveConsistentNaming() {
        assertThat(OrderStatus.PLACED.name()).isEqualTo("PLACED");
        assertThat(OrderStatus.PAID.name()).isEqualTo("PAID");
        assertThat(OrderStatus.PROCESSED.name()).isEqualTo("PROCESSED");
        assertThat(OrderStatus.SENT.name()).isEqualTo("SENT");
    }
}