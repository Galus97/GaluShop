package pl.galushop.GaluShop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.component.OrderStatus;
import pl.galushop.GaluShop.dto.request.OrderRequest;
import pl.galushop.GaluShop.dto.request.ProductQuantityRequest;
import pl.galushop.GaluShop.entity.Order;
import pl.galushop.GaluShop.entity.OrderProduct;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    @BeforeEach
    void setUp(){
        User user = new User();
        user.setUserId(1L);
        order = Order.builder()
                .orderId(1L)
                .localDateTime(LocalDateTime.of(2025, 3, 26, 12,12))
                .status(OrderStatus.PROCESSED)
                .user(user)
                .build();

        Product product = new Product();
        product.setProductId(1L);
        List<OrderProduct> orderProducts = Arrays.asList(new OrderProduct(order, product, 10));
        order.setOrderProducts(orderProducts);

        ProductQuantityRequest productQuantityRequest = new ProductQuantityRequest();
        productQuantityRequest.setProductId(1L);
        productQuantityRequest.setQuantity(10);
        List<ProductQuantityRequest> productQuantityRequests = Arrays.asList(productQuantityRequest);
        orderRequest = new OrderRequest();
        orderRequest.setOrderId(1L);
        orderRequest.setUserId(1L);
        orderRequest.setLocalDateTime(LocalDateTime.of(2025, 3, 26, 12,12));
        orderRequest.setOrderStatus(OrderStatus.PROCESSED);
        orderRequest.setProductQuantityRequests(productQuantityRequests);
    }
}