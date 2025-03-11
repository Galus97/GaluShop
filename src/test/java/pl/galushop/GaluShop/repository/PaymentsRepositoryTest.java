package pl.galushop.GaluShop.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import pl.galushop.GaluShop.component.OrderStatus;
import pl.galushop.GaluShop.component.PaymentStatus;
import pl.galushop.GaluShop.entity.Order;
import pl.galushop.GaluShop.entity.Payments;
import pl.galushop.GaluShop.entity.User;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class PaymentsRepositoryTest {
    @Autowired
    TestEntityManager entityManager;
    @Autowired
    PaymentsRepository paymentsRepository;

    private Payments payments;

    @BeforeEach
    void setUp(){
        User persistedUser = entityManager.persistAndFlush(User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@gmail.com")
                .password("{noop}secretPassword")
                .enabled(true)
                .emailCode("1111")
                .build());
        Order persistedOrder = entityManager.persistAndFlush(Order.builder()
                .localDateTime(LocalDateTime.of(2025, 3, 10, 12, 12))
                .status(OrderStatus.PROCESSED)
                .user(persistedUser)
                .build());
        payments = Payments.builder()
                .totalAmount(100d)
                .paymentStatus(PaymentStatus.NEW)
                .order(persistedOrder)
                .user(persistedUser)
                .build();
        entityManager.persistAndFlush(payments);
    }

    @Test
    void givenExistingPayment_whenFindByOrderId_thenReturnPayment(){
        //when
        Optional<Payments> optionalPayment = paymentsRepository.findByOrder_OrderId(payments.getOrder().getOrderId());
        //then
        assertTrue(optionalPayment.isPresent());
        assertEquals(payments, optionalPayment.get());
    }

    @Test
    void givenNonExistentOrderId_whenFindByOrderId_thenOptionalEmpty(){
        //when
        Optional<Payments> optionalPayment = paymentsRepository.findByOrder_OrderId(9999L);
        //then
        assertFalse(optionalPayment.isPresent());
    }
}