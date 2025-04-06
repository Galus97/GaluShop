package pl.galushop.GaluShop.dto.response;

import org.junit.jupiter.api.Test;
import pl.galushop.GaluShop.entity.Order;
import pl.galushop.GaluShop.entity.OrderProduct;
import pl.galushop.GaluShop.entity.Product;

import static org.junit.jupiter.api.Assertions.*;

class OrderProductResponseTest {

    @Test
    void givenOrderProduct_whenFromEntity_thenReturnsCorrectResponse(){
        //given
        Order order = new Order();
        order.setOrderId(1L);
        Product product = new Product();
        product.setProductId(2L);
        OrderProduct orderProduct = new OrderProduct(order, product, 15);
        //when
        OrderProductResponse response = OrderProductResponse.fromEntity(orderProduct);
        //then
        assertEquals(1L, orderProduct.getOrder().getOrderId());
        assertEquals(2L, orderProduct.getProduct().getProductId());
        assertEquals(15, orderProduct.getQuantity());
    }
}