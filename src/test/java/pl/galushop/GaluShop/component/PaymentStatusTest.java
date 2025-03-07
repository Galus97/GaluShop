package pl.galushop.GaluShop.component;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PaymentStatusTest {

    @Test
    void shouldContainAllRequiredStatusesInCorrectOrder() {
        PaymentStatus[] values = PaymentStatus.values();

        assertThat(values)
                .containsExactly(
                        PaymentStatus.NEW,
                        PaymentStatus.PENDING,
                        PaymentStatus.CONFIRMED,
                        PaymentStatus.REJECTED,
                        PaymentStatus.ABANDONED,
                        PaymentStatus.EXPIRED,
                        PaymentStatus.ERROR
                );
    }

}