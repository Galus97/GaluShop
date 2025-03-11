package pl.galushop.GaluShop.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;
import pl.galushop.GaluShop.component.OrderStatus;
import pl.galushop.GaluShop.entity.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
class OrderProductRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;
    @Autowired
    OrderProductRepository orderProductRepository;

    private OrderProduct orderProduct;

    @BeforeEach
    void setUp(){
        User persistedUser = testEntityManager.persistAndFlush(new User());

        Order persistedOrder = testEntityManager.persistAndFlush(Order.builder()
                .localDateTime(LocalDateTime.of(2025, 3, 10, 12, 12))
                .status(OrderStatus.PROCESSED)
                .user(persistedUser)
                .build());

        Product persistedProduct = testEntityManager.persistAndFlush(Product.builder()
                .productName("Phone")
                .description("Valid description of phone")
                .price(550d)
                .category("Category 1")
                .categoryId(1)
                .build());

        orderProduct = OrderProduct.builder()
                .id(new OrderProductId(persistedOrder.getOrderId(), persistedProduct.getProductId()))
                .order(persistedOrder)
                .product(persistedProduct)
                .quantity(1)
                .build();
        testEntityManager.persistAndFlush(orderProduct);
    }

    @Test
    void givenExistingOrder_whenFindByOrderId_thenReturnOrderProductList(){
        //when
        List<OrderProduct> orderProductList = orderProductRepository.findByOrder_OrderId(orderProduct.getOrder().getOrderId());
        //then
        assertThat(orderProductList).hasSize(1).contains(orderProduct);
    }

    @Test
    void givenNonExistentOrder_whenFindByOrderId_thenReturnEmptyList(){
        //when
        List<OrderProduct> orderProductList = orderProductRepository.findByOrder_OrderId(9999L);
        //then
        assertThat(orderProductList).isEmpty();
    }

    @Test
    void givenInvalidOrderId_whenFindByOrderId_thenReturnEmptyList(){
        //when
        List<OrderProduct> orderProductList = orderProductRepository.findByOrder_OrderId(-1L);
        //then
        assertThat(orderProductList).isEmpty();
    }

    @Test
    void givenNullOrderId_whenFindByOrderId_thenReturnEmptyList(){
        //when
        List<OrderProduct> orderProductList = orderProductRepository.findByOrder_OrderId(null);
        //then
        assertThat(orderProductList).isEmpty();
    }

    @Test
    void givenExistingProduct_whenFindByProductId_thenReturnOrderProductList(){
        //when
        List<OrderProduct> orderProductList = orderProductRepository.findByProduct_ProductId(orderProduct.getProduct().getProductId());
        //then
        assertThat(orderProductList).hasSize(1).contains(orderProduct);
    }

    @Test
    void givenNonExistentProduct_whenFindByProductId_thenReturnEmptyList(){
        //when
        List<OrderProduct> orderProductList = orderProductRepository.findByProduct_ProductId(9999L);
        //then
        assertThat(orderProductList).isEmpty();
    }

    @Test
    void givenInvalidProductId_whenFindByProductId_thenReturnEmptyList(){
        //when
        List<OrderProduct> orderProductList = orderProductRepository.findByProduct_ProductId(-1L);
        //then
        assertThat(orderProductList).isEmpty();
    }

    @Test
    void givenNullProductId_whenFindByProductId_thenReturnEmptyList(){
        //when
        List<OrderProduct> orderProductList = orderProductRepository.findByProduct_ProductId(null);
        //then
        assertThat(orderProductList).isEmpty();
    }
}
