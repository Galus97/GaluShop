package pl.galushop.GaluShop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.component.OrderStatus;
import pl.galushop.GaluShop.dto.request.OrderRequest;
import pl.galushop.GaluShop.dto.request.ProductQuantityRequest;
import pl.galushop.GaluShop.dto.response.OrderResponse;
import pl.galushop.GaluShop.model.Order;
import pl.galushop.GaluShop.model.OrderProduct;
import pl.galushop.GaluShop.model.Product;
import pl.galushop.GaluShop.model.User;
import pl.galushop.GaluShop.exception.OrderNotFoundException;
import pl.galushop.GaluShop.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    OrderRepository repository;
    @Mock
    UserService userService;
    @Mock
    MessageService messageService;
    @Mock
    ProductService productService;
    @InjectMocks
    OrderService service;
    private Order order;
    private OrderRequest orderRequest;
    private User user;
    private Product product;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1L);
        order = Order.builder()
                .orderId(1L)
                .localDateTime(LocalDateTime.of(2025, 3, 26, 12, 12))
                .status(OrderStatus.PROCESSED)
                .user(user)
                .build();

        product = new Product();
        product.setProductId(1L);
        List<OrderProduct> orderProducts = Arrays.asList(new OrderProduct(order, product, 10));
        order.setOrderProducts(orderProducts);

        ProductQuantityRequest productQuantityRequest = new ProductQuantityRequest();
        productQuantityRequest.setProductId(1L);
        productQuantityRequest.setQuantity(10);
        List<ProductQuantityRequest> productQuantityRequests = Arrays.asList(productQuantityRequest);
        orderRequest = OrderRequest.builder()
                .orderId(1L)
                .userId(1L)
                .localDateTime(LocalDateTime.of(2025, 3, 30, 12, 12))
                .orderStatus(OrderStatus.PROCESSED)
                .productQuantityRequests(productQuantityRequests)
                .build();
    }

    @Test
    void givenExistingId_whenGetOrderResponse_thenReturnsOrderResponse() {
        //given
        when(repository.findById(anyLong())).thenReturn(Optional.of(order));
        //when
        OrderResponse response = service.getOrderResponse(1L);
        //then
        assertNotNull(response);
        assertEquals(1L, response.orderId());
        assertEquals(OrderStatus.PROCESSED, response.status());
        assertEquals(1L, response.userId());
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void givenNonExistentId_whenGetOrderResponse_thenThrowsException() {
        //given
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(OrderNotFoundException.class, () -> service.getOrderResponse(1L));
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void givenInvalidId_whenGetOrderResponse_thenThrowsException() {
        //then
        assertThrows(IllegalArgumentException.class, () -> service.getOrderResponse(-1L));
        verify(repository, times(0)).findById(anyLong());
    }

    @Test
    void givenNullId_whenGetOrderResponse_thenThrowsException() {
        //then
        assertThrows(IllegalArgumentException.class, () -> service.getOrderResponse(null));
        verify(repository, times(0)).findById(anyLong());
    }

    @Test
    void givenExistingId_whenGetOrderEntity_thenReturnsOrderEntity() {
        //given
        when(repository.findById(anyLong())).thenReturn(Optional.of(order));
        //when
        Order orderEntity = service.getOrderEntity(1L);
        //then
        assertNotNull(orderEntity);
        assertEquals(1L, orderEntity.getOrderId());
        assertEquals(OrderStatus.PROCESSED, orderEntity.getStatus());
        assertEquals(1L, orderEntity.getUser().getUserId());
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void givenCorrectOrder_whenSaveOrder_thenReturnsOrderResponse() {
        //given
        when(userService.getUserEntity(anyLong())).thenReturn(user);
        when(productService.getAllProductByIds(any())).thenReturn(Arrays.asList(product));
        when(repository.save(any(Order.class))).thenReturn(order);
        //when
        OrderResponse response = service.saveOrder(orderRequest);
        //then
        assertNotNull(response);
        assertEquals(orderRequest.getUserId(), response.userId());
        assertEquals(orderRequest.getOrderStatus(), response.status());
        assertEquals(orderRequest.getProductQuantityRequests().size(), response.products().size());
    }

    @Test
    void givenExistingId_whenDeleteOrder_thenDeletesOrder() {
        //given
        when(repository.findById(anyLong())).thenReturn(Optional.of(order));
        //when
        service.deleteOrder(1L);
        //then
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).delete(order);
    }

    @Test
    void givenCorrectRequest_whenUpdateOrder_thenReturnsOrderResponse(){
        //given
        when(repository.findById(anyLong())).thenReturn(Optional.of(order));
        when(userService.getUserEntity(anyLong())).thenReturn(user);
        when(productService.getAllProductByIds(any())).thenReturn(Arrays.asList(product));
        when(repository.save(any(Order.class))).thenReturn(order);
        //when
        OrderResponse response = service.updateOrder(orderRequest);
        //then
        assertNotNull(response);
        assertEquals(orderRequest.getUserId(), response.userId());
        assertEquals(orderRequest.getOrderStatus(), response.status());
        assertEquals(orderRequest.getProductQuantityRequests().size(), response.products().size());
    }
}