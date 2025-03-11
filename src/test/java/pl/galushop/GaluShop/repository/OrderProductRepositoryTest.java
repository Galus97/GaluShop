package pl.galushop.GaluShop.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

@DataJpaTest
class OrderProductRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;
    @Autowired
    OrderProductRepository orderProductRepository;
    private OrderProduct orderProduct;
    private Long orderId;
    private Long productId;
    @BeforeEach
    void setUp(){
        User user = new User();
        User persistedUser = testEntityManager.persistAndFlush(user);

        Order order = Order.builder()
                .localDateTime(LocalDateTime.of(2025, 3, 10, 12, 12))
                .status(OrderStatus.PROCESSED)
                .user(persistedUser)
                .build();

        Product product = Product.builder()
                .productName("Phone")
                .description("Valid description of phone")
                .price(550d)
                .category("Category 1")
                .categoryId(1)
                .build();

        Order persistedOrder = testEntityManager.persistAndFlush(order);
        Product persistedProduct = testEntityManager.persistAndFlush(product);

        orderId = persistedOrder.getOrderId();
        productId = persistedProduct.getProductId();

        OrderProductId orderProductId = new OrderProductId(orderId, productId);

        orderProduct = OrderProduct.builder()
                .id(orderProductId)
                .order(persistedOrder)
                .product(persistedProduct)
                .quantity(1)
                .build();
        testEntityManager.persistAndFlush(orderProduct);

    }

    @Test
    void givenExistedOrder_whenFindByOrder_OrderId_thenReturnSuccess(){
        //when
        List<OrderProduct> orderProductList = orderProductRepository.findByOrder_OrderId(orderId);
        //then
        assertThat(orderProductList)
                .hasSize(1)
                .contains(orderProduct);
    }

    @Test
    void givenNonExistedOrder_whenFindByOrder_OrderId_thenReturnEmptyList(){
        //when
        List<OrderProduct> orderProductList = orderProductRepository.findByOrder_OrderId(5L);
        //then
        assertThat(orderProductList).isEmpty();
    }

    @Test
    void givenNegativeNumber_whenFindByOrder_OrderId_thenReturnEmptyList(){
        //when
        List<OrderProduct> orderProductList = orderProductRepository.findByOrder_OrderId(-5L);
        //then
        assertThat(orderProductList).isEmpty();
    }

    @Test
    void givenExistedProduct_whenFindByProduct_ProductId_thenReturnSuccess(){
        //when
        List<OrderProduct> orderProductList = orderProductRepository.findByProduct_ProductId(productId);
        //then
        assertThat(orderProductList)
                .hasSize(1)
                .contains(orderProduct);
    }

    @Test
    void givenNonExistedProduct_whenFindByProduct_ProductId_thenReturnEmptyList(){
        //when
        List<OrderProduct> orderProductList = orderProductRepository.findByProduct_ProductId(5L);
        //then
        assertThat(orderProductList).isEmpty();
    }

    @Test
    void givenNegativeNumber_whenFindByProduct_ProductId_thenReturnEmptyList(){
        //when
        List<OrderProduct> orderProductList = orderProductRepository.findByProduct_ProductId(-5L);
        //then
        assertThat(orderProductList).isEmpty();
    }
}