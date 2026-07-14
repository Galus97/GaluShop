package pl.galushop.GaluShop.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;
import pl.galushop.GaluShop.component.OrderStatus;
import pl.galushop.GaluShop.model.Order;
import pl.galushop.GaluShop.model.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
class OrderRepositoryTest {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    TestEntityManager testEntityManager;
    private Order order;
    @BeforeEach
    void setUp(){
        User persistedUser = testEntityManager.persistAndFlush(User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@gmail.com")
                .password("{noop}secretPassword")
                .enabled(true)
                .emailCode("1111")
                .build());


         order = Order.builder()
                .localDateTime(LocalDateTime.of(2025, 3, 10, 12, 12))
                .status(OrderStatus.PROCESSED)
                .user(persistedUser)
                .build();
        testEntityManager.persistAndFlush(order);
    }

    @Test
    void givenExistedOrder_whenFindAllByUser_UserId_thenReturnOrderList(){
        //when
        List<Order> orderList = orderRepository.findAllByUser_UserId(order.getUser().getUserId());
        //then
        assertThat(orderList).hasSize(1).contains(order);
    }

    @Test
    void givenNonExistentUser_whenFindAllByUser_UserId_thenReturnEmptyList(){
        //when
        List<Order> orderList = orderRepository.findAllByUser_UserId(9999L);
        //then
        assertThat(orderList).isEmpty();
    }

    @Test
    void givenInvalidUserId_whenFindAllByUser_UserId_thenReturnEmptyList(){
        //when
        List<Order> orderList = orderRepository.findAllByUser_UserId(-1L);
        //then
        assertThat(orderList).isEmpty();
    }

    @Test
    void givenNullUserId_whenFindAllByUser_UserId_thenReturnEmptyList(){
        //when
        List<Order> orderList = orderRepository.findAllByUser_UserId(null);
        //then
        assertThat(orderList).isEmpty();
    }
}