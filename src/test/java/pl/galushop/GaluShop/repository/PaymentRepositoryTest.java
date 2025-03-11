package pl.galushop.GaluShop.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import pl.galushop.GaluShop.component.OrderStatus;
import pl.galushop.GaluShop.component.PaymentStatus;
import pl.galushop.GaluShop.entity.Order;
import pl.galushop.GaluShop.entity.Payment;
import pl.galushop.GaluShop.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class PaymentRepositoryTest {
    @Autowired
    TestEntityManager entityManager;
    @Autowired
    PaymentRepository paymentRepository;

    private Payment payment;

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
        payment = Payment.builder()
                .totalAmount(100d)
                .paymentStatus(PaymentStatus.NEW)
                .order(persistedOrder)
                .user(persistedUser)
                .build();
        entityManager.persistAndFlush(payment);
    }

    @Test
    void givenExistingPayment_whenFindByOrderId_thenReturnPayment(){
        //when
        Optional<Payment> optionalPayment = paymentRepository.findByOrder_OrderId(payment.getOrder().getOrderId());
        //then
        assertTrue(optionalPayment.isPresent());
        assertEquals(payment, optionalPayment.get());
    }

    @Test
    void givenNonExistentOrderId_whenFindByOrderId_thenOptionalEmpty(){
        //when
        Optional<Payment> optionalPayment = paymentRepository.findByOrder_OrderId(9999L);
        //then
        assertFalse(optionalPayment.isPresent());
    }

    @Test
    void givenInvalidOrderId_whenFindByOrderId_thenOptionalEmpty(){
        //when
        Optional<Payment> optionalPayment = paymentRepository.findByOrder_OrderId(-1L);
        //then
        assertFalse(optionalPayment.isPresent());
    }

    @Test
    void givenNullOrderId_whenFindByOrderId_thenOptionalEmpty(){
        //when
        Optional<Payment> optionalPayment = paymentRepository.findByOrder_OrderId(null);
        //then
        assertFalse(optionalPayment.isPresent());
    }

    @Test
    void givenExistingPayment_whenFindAllByUserId_thenReturnPaymentList(){
        //when
        List<Payment> paymentList = paymentRepository.findAllByUser_UserId(payment.getUser().getUserId());
        //then
        assertThat(paymentList)
                .hasSize(1)
                .contains(payment);
    }

    @Test
    void givenNonExistentUserId_whenFindAllByUserId_thenReturnEmptyList(){
        //when
        List<Payment> paymentList = paymentRepository.findAllByUser_UserId(9999L);
        //then
        assertThat(paymentList).isEmpty();

    }

    @Test
    void givenInvalidUserId_whenFindAllByUserId_thenReturnEmptyList(){
        //when
        List<Payment> paymentList = paymentRepository.findAllByUser_UserId(-1L);
        //then
        assertThat(paymentList).isEmpty();
    }

    @Test
    void givenNullUserId_whenFindAllByUserId_thenReturnEmptyList(){
        //when
        List<Payment> paymentList = paymentRepository.findAllByUser_UserId(null);
        //then
        assertThat(paymentList).isEmpty();
    }
}