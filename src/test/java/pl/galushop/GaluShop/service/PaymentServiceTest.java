package pl.galushop.GaluShop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.component.PaymentStatus;
import pl.galushop.GaluShop.dto.request.PaymentRequest;
import pl.galushop.GaluShop.dto.response.PaymentResponse;
import pl.galushop.GaluShop.entity.Order;
import pl.galushop.GaluShop.entity.Payment;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.exception.PaymentNotFoundException;
import pl.galushop.GaluShop.repository.PaymentRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @Mock
    PaymentRepository repository;
    @Mock
    MessageService messageService;
    @Mock
    OrderService orderService;
    @Mock
    UserService userService;
    @InjectMocks
    PaymentService service;
    private Payment payment;
    private PaymentRequest request;
    private User user;

    @BeforeEach
    void setUp(){
        Order order = new Order();
        order.setOrderId(1L);
        user = new User();
        user.setUserId(1L);
        payment = Payment.builder()
                .paymentId(1L)
                .totalAmount(100d)
                .paymentStatus(PaymentStatus.NEW)
                .order(order)
                .user(user)
                .build();
        request = new PaymentRequest();
        request.setPaymentId(1L);
        request.setTotalAmount(100d);
        request.setPaymentStatus(PaymentStatus.NEW);
        request.setOrderId(1L);
        request.setUserId(1L);
    }

    @Test
    void givenExistingId_whenGetPaymentResponse_thenReturnsPaymentResponse(){
        //given
        when(repository.findById(anyLong())).thenReturn(Optional.of(payment));
        //when
        PaymentResponse response = service.getPaymentResponse(1L);
        //then
        assertNotNull(response);
        assertEquals(1L, response.paymentId());
        assertEquals(100.0, response.totalAmount());
        assertEquals(PaymentStatus.NEW, response.paymentStatus());
        assertEquals(1L, response.orderId());
        assertEquals(1L, response.paymentId());

        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void givenNonExistentId_whenGetPaymentResponse_thenThrowsException(){
        //given
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(PaymentNotFoundException.class, () -> service.getPaymentResponse(1L));
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void givenInvalidId_whenGetPaymentResponse_thenThrowsException(){
        //then
        assertThrows(IllegalArgumentException.class, () -> service.getPaymentResponse(-1L));
        verify(repository, times(0)).findById(anyLong());
    }

    @Test
    void givenNullId_whenGetPaymentResponse_thenThrowsException(){
        //then
        assertThrows(IllegalArgumentException.class, () -> service.getPaymentResponse(null));
        verify(repository, times(0)).findById(anyLong());
    }

    @Test
    void givenExistingId_whenPaymentResponseByOrderId_thenReturnsPaymentResponse(){
        //given
        when(repository.findByOrder_OrderId(anyLong())).thenReturn(Optional.of(payment));
        //when
        PaymentResponse response = service.getPaymentResponseByOrderId(1L);
        //then
        assertNotNull(response);
        assertEquals(1L, response.paymentId());
        assertEquals(100.0, response.totalAmount());
        assertEquals(PaymentStatus.NEW, response.paymentStatus());
        assertEquals(1L, response.orderId());
        assertEquals(1L, response.paymentId());

        verify(repository, times(1)).findByOrder_OrderId(anyLong());
    }

    @Test
    void givenNonExistentId_whenPaymentResponseByOrderId_thenThrowsException(){
        //given
        when(repository.findByOrder_OrderId(anyLong())).thenReturn(Optional.empty());
        //when
        assertThrows(PaymentNotFoundException.class, () ->  service.getPaymentResponseByOrderId(1L));
        //then
        verify(repository, times(1)).findByOrder_OrderId(anyLong());
    }

    @Test
    void givenExistingId_whenAllPaymentResponseByUserId_thenReturnsPaymentResponseList(){
        //given
        List<Payment> paymentList = Arrays.asList(payment);
        when(repository.findAllByUser_UserId(anyLong())).thenReturn(paymentList);
        when(userService.getUserEntity(anyLong())).thenReturn(user);
        //when
        List<PaymentResponse> response = service.getAllPaymentResponseByUserId(1L);
        //then
        assertEquals(1, response.size());
        assertEquals(1L, response.get(0).paymentId());
        assertEquals(100d, response.get(0).totalAmount());
        assertEquals(PaymentStatus.NEW, response.get(0).paymentStatus());
        verify(repository, times(1)).findAllByUser_UserId(anyLong());
    }
}