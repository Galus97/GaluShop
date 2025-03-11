package pl.galushop.GaluShop.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import pl.galushop.GaluShop.component.OrderStatus;
import pl.galushop.GaluShop.entity.Order;
import pl.galushop.GaluShop.entity.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrderRepositoryTest {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    TestEntityManager testEntityManager;
    private Order order;
    private Long userId;
    @BeforeEach
    void setUp(){
        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@gmail.com")
                .password("{noop}secretPassword")
                .enabled(true)
                .emailCode("1111")
                .build();
        User persistedUser = testEntityManager.persistAndFlush(user);
        userId = persistedUser.getUserId();

         order = Order.builder()
                .localDateTime(LocalDateTime.of(2025, 3, 10, 12, 12))
                .status(OrderStatus.PROCESSED)
                .user(persistedUser)
                .build();
        testEntityManager.persistAndFlush(order);
    }

    @Test
    void givenExistedOrder_whenFindAllByUser_UserId_thenReturnSuccess(){
        //when
        List<Order> orderList = orderRepository.findAllByUser_UserId(userId);
        //then
        assertThat(orderList)
                .hasSize(1)
                .contains(order);
    }
}