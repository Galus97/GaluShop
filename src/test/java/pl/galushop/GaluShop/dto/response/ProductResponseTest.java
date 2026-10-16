package pl.galushop.GaluShop.dto.response;

import org.junit.jupiter.api.Test;
import pl.galushop.GaluShop.model.Order;
import pl.galushop.GaluShop.model.OrderItem;
import pl.galushop.GaluShop.model.Product;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductResponseTest {

    @Test
    void givenProduct_whenFromEntity_thenReturnsCorrectResponse(){
        //given
        Order order = new Order();
        order.setOrderId(1L);

        Product product = new Product();
        product.setProductId(1L);

        OrderItem orderItem = new OrderItem(order, product, 15);
        List<OrderItem> orderItems = Arrays.asList(orderItem);

        product.setProductName("Product name");
        product.setDescription("Description of the product");
        product.setPrice(10d);
        product.setCategory("Category of the product");
        product.setCategoryId(1);
        product.setOrderItems(orderItems);
        //when
        ProductResponse response = ProductResponse.fromEntity(product);
        //then
        assertEquals(1L, response.productId());
        assertEquals("Product name", response.productName());
        assertEquals("Description of the product", response.description());
        assertEquals(10.0, response.price());
        assertEquals("Category of the product", response.category());
        assertEquals(1, response.categoryId());
        assertEquals(1, response.products().size());
        assertEquals(15, response.products().get(0).quantity());
    }
}