package pl.galushop.GaluShop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import pl.galushop.GaluShop.component.OrderStatus;
import pl.galushop.GaluShop.configuration.SpringSecurity;
import pl.galushop.GaluShop.dto.request.OrderRequest;
import pl.galushop.GaluShop.dto.request.ProductQuantityRequest;
import pl.galushop.GaluShop.dto.response.OrderProductResponse;
import pl.galushop.GaluShop.dto.response.OrderResponse;
import pl.galushop.GaluShop.service.OrderService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@WebMvcTest
@Import(SpringSecurity.class)
@AutoConfigureMockMvc(addFilters = false)
class OrderControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    OrderService orderService;
    private OrderRequest request;
    private OrderResponse response;

    @BeforeEach
    void setUp(){
        ProductQuantityRequest productQuantityRequest = new ProductQuantityRequest();
        productQuantityRequest.setProductId(1L);
        productQuantityRequest.setQuantity(10);
        List<ProductQuantityRequest> productQuantityRequests = Arrays.asList(productQuantityRequest);

        request = OrderRequest.builder()
                .orderId(1L)
                .userId(1L)
                .localDateTime(LocalDateTime.of(2025, 3, 30, 12, 12))
                .orderStatus(OrderStatus.PROCESSED)
                .productQuantityRequests(productQuantityRequests)
                .build();

        List<OrderProductResponse> products = Arrays.asList(new OrderProductResponse(1L, 1L, 10));
        response = new OrderResponse(
                1L,
                LocalDateTime.of(2025, 3, 30, 12, 12),
                OrderStatus.PROCESSED,
                1L,
                products);

    }
}