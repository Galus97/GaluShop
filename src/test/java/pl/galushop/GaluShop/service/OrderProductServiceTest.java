package pl.galushop.GaluShop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.entity.Order;
import pl.galushop.GaluShop.entity.OrderProduct;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.repository.OrderProductRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderProductServiceTest {
    @Mock
    private OrderProductRepository orderProductRepository;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private OrderProductService orderProductService;

    private Order order;
    private Product product;
    private OrderProduct orderProduct;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setOrderId(1L);

        product = new Product();
        product.setProductId(1L);

        orderProduct = new OrderProduct(order, product, 2);
    }
    @Test
    void givenExistingOrderProduct_whenSaveOrderProduct_thenSaveCorrectly() {
        // Act
        orderProductService.saveOrderProduct(orderProduct);

        // Assert
        verify(orderProductRepository, times(1)).save(orderProduct);
    }
}