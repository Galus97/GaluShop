package pl.galushop.GaluShop.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import pl.galushop.GaluShop.component.OrderStatus;
import pl.galushop.GaluShop.entity.Order;
import pl.galushop.GaluShop.entity.OrderProduct;
import pl.galushop.GaluShop.entity.OrderProductId;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.entity.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class OrderProductRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;
    @Autowired
    OrderProductRepository orderProductRepository;
    OrderProduct orderProduct;
    @BeforeEach
    void setUp(){
        User user = new User();

        Order order = Order.builder()
                .localDateTime(LocalDateTime.of(2025, 3, 10, 12, 12))
                .status(OrderStatus.PROCESSED)
                .user(user)
                .build();

        Product product = Product.builder()
                .productName("Phone")
                .description("Valid description of phone")
                .price(550d)
                .category("Category 1")
                .categoryId(1)
                .build();

        testEntityManager.persistAndFlush(user);
        testEntityManager.persistAndFlush(order);
        testEntityManager.persistAndFlush(product);

        OrderProductId orderProductId = new OrderProductId(order.getOrderId(), product.getProductId());

        orderProduct = OrderProduct.builder()
                .id(orderProductId)
                .order(order)
                .product(product)
                .quantity(1)
                .build();
    }

    @Test
    void givenExistedOrder_whenFindByOrder_OrderId_thenReturnSuccess(){
        //given
        testEntityManager.persistAndFlush(orderProduct);
        //when
        List<OrderProduct> orderProductList = orderProductRepository.findByOrder_OrderId(1L);
        //then
        assertThat(orderProductList)
                .hasSize(1)
                .contains(orderProduct);
    }
}