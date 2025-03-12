package pl.galushop.GaluShop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.dto.OrderProductDto;
import pl.galushop.GaluShop.entity.Order;
import pl.galushop.GaluShop.entity.OrderProduct;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.repository.OrderProductRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Test
    void givenNullOrderProduct_whenSaveOrderProduct_thenThrowIllegalArgumentException(){
        //Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> orderProductService.saveOrderProduct(null));
    }

    @Test
    void givenExistingId_whenGetOrderProductsByOrderId_thenReturnOrderProductDtoList() {
        // Arrange
        when(orderProductRepository.findByOrder_OrderId(1L)).thenReturn(List.of(orderProduct));
        // Act
        List<OrderProductDto> result = orderProductService.getOrderProductsByOrderId(1L);
        // Assert
        assertEquals(1, result.size());
        verify(orderProductRepository, times(1)).findByOrder_OrderId(1L);
    }

    @Test
    void givenInvalidId_whenGetOrderProductsByOrderId_ThrowIllegalArgumentException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> orderProductService.getOrderProductsByOrderId(-1L));
    }

    @Test
    void givenNonExistentId_whenGetOrderProductsByOrderId_thenReturnEmptyList() {
        // Arrange
        when(orderProductRepository.findByOrder_OrderId(1L)).thenReturn(Collections.emptyList());
        // Act
        List<OrderProductDto> result = orderProductService.getOrderProductsByOrderId(1L);
        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void givenExistingId_whenOrderProductsByProductId_thenReturnOrderProductDtoList() {
        // Arrange
        when(orderProductRepository.findByProduct_ProductId(1L)).thenReturn(List.of(orderProduct));
        // Act
        List<OrderProductDto> result = orderProductService.getOrderProductsByProductId(1L);
        // Assert
        assertEquals(1, result.size());
        verify(orderProductRepository, times(1)).findByProduct_ProductId(1L);
    }

    @Test
    void givenInvalidId_whenOrderProductsByProductId_ThrowIllegalArgumentException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> orderProductService.getOrderProductsByProductId(-1L));
    }
}