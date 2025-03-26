package pl.galushop.GaluShop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.component.PaymentStatus;
import pl.galushop.GaluShop.dto.request.PaymentRequest;
import pl.galushop.GaluShop.entity.Order;
import pl.galushop.GaluShop.entity.Payment;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.repository.PaymentRepository;

import static org.junit.jupiter.api.Assertions.*;

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

    @BeforeEach
    void setUp(){
        Order order = new Order();
        order.setOrderId(1L);
        User user = new User();
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
}