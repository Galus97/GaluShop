package pl.galushop.GaluShop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.dto.response.OrderProductResponse;
import pl.galushop.GaluShop.model.Order;
import pl.galushop.GaluShop.model.OrderProduct;
import pl.galushop.GaluShop.model.Product;
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
    private OrderProductRepository repository;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private OrderProductService service;

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
        //given
        when(repository.save(orderProduct)).thenReturn(orderProduct);
        //when
        OrderProductResponse orderProductResponse = service.saveOrderProduct(orderProduct);
        //then
        assertEquals(1L, orderProductResponse.orderId());
        assertEquals(1L, orderProductResponse.productId());
        verify(repository, times(1)).save(orderProduct);
    }

    @Test
    void givenNullOrderProduct_whenSaveOrderProduct_thenThrowIllegalArgumentException(){
        //then
        assertThrows(IllegalArgumentException.class,
                () -> service.saveOrderProduct(null));
    }

    @Test
    void givenExistingId_whenGetOrderProductsByOrderId_thenReturnOrderProductDtoList() {
        //given
        when(repository.findByOrder_OrderId(1L)).thenReturn(List.of(orderProduct));
        //when
        List<OrderProductResponse> result = service.getOrderProductsByOrderId(1L);
        //then
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).orderId());
        assertEquals(1L, result.get(0).productId());
        verify(repository, times(1)).findByOrder_OrderId(1L);
    }

    @Test
    void givenInvalidId_whenGetOrderProductsByOrderId_ThrowIllegalArgumentException() {
        //then
        assertThrows(IllegalArgumentException.class,
                () -> service.getOrderProductsByOrderId(-1L));
    }

    @Test
    void givenNonExistentId_whenGetOrderProductsByOrderId_thenReturnEmptyList() {
        //given
        when(repository.findByOrder_OrderId(1L)).thenReturn(Collections.emptyList());
        //when
        List<OrderProductResponse> result = service.getOrderProductsByOrderId(1L);
        //then
        assertTrue(result.isEmpty());
    }

    @Test
    void givenExistingId_whenOrderProductsByProductId_thenReturnOrderProductDtoList() {
        // given
        when(repository.findByProduct_ProductId(1L)).thenReturn(List.of(orderProduct));
        //when
        List<OrderProductResponse> result = service.getOrderProductsByProductId(1L);
        //then
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).orderId());
        assertEquals(1L, result.get(0).productId());
        verify(repository, times(1)).findByProduct_ProductId(1L);
    }

    @Test
    void givenInvalidId_whenOrderProductsByProductId_ThrowIllegalArgumentException() {
        //then
        assertThrows(IllegalArgumentException.class,
                () -> service.getOrderProductsByProductId(-1L));
    }

    @Test
    void givenNonExistentId_whenOrderProductsByProductId_thenReturnEmptyList() {
        //given
        when(repository.findByProduct_ProductId(1L)).thenReturn(Collections.emptyList());
        //when
        List<OrderProductResponse> result = service.getOrderProductsByProductId(1L);
        //then
        assertTrue(result.isEmpty());
    }
}