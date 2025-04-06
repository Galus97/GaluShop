package pl.galushop.GaluShop.dto.response;

import org.junit.jupiter.api.Test;
import pl.galushop.GaluShop.component.OrderStatus;
import pl.galushop.GaluShop.entity.Order;
import pl.galushop.GaluShop.entity.OrderProduct;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.entity.User;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderResponseTest {

    @Test
    void givenOrder_whenFromEntity_thenReturnsCorrectResponse(){
        //given
        User user = new User();
        user.setUserId(1L);

        Order order = new Order();
        order.setOrderId(1L);

        Product product = new Product();
        product.setProductId(2L);

        OrderProduct orderProduct = new OrderProduct(order, product, 15);
        List<OrderProduct> orderProducts = Arrays.asList(orderProduct);

        order.setLocalDateTime(LocalDateTime.of(2025, 4, 6, 10, 10));
        order.setStatus(OrderStatus.PROCESSED);
        order.setUser(user);
        order.setOrderProducts(orderProducts);
        //when
        OrderResponse response = OrderResponse.fromEntity(order);
        //then
        assertEquals(1L, response.orderId());
        assertEquals(LocalDateTime.of(2025, 4, 6, 10, 10), response.localDateTime());
        assertEquals(OrderStatus.PROCESSED, response.status());
        assertEquals(1L, response.userId());
        assertEquals(1, response.products().size());
        assertEquals(15, response.products().get(0).quantity());

    }
}