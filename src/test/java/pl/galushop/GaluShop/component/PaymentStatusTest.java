package pl.galushop.GaluShop.component;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    void shouldConvertFromStringCorrectly() {
        assertThat(PaymentStatus.valueOf("NEW")).isEqualTo(PaymentStatus.NEW);
        assertThat(PaymentStatus.valueOf("PENDING")).isEqualTo(PaymentStatus.PENDING);
        assertThat(PaymentStatus.valueOf("REJECTED")).isEqualTo(PaymentStatus.REJECTED);
        assertThat(PaymentStatus.valueOf("CONFIRMED")).isEqualTo(PaymentStatus.CONFIRMED);
        assertThat(PaymentStatus.valueOf("ABANDONED")).isEqualTo(PaymentStatus.ABANDONED);
        assertThat(PaymentStatus.valueOf("EXPIRED")).isEqualTo(PaymentStatus.EXPIRED);
        assertThat(PaymentStatus.valueOf("ERROR")).isEqualTo(PaymentStatus.ERROR);
    }
}