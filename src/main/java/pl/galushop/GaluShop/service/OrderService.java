package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.component.OrderStatus;
import pl.galushop.GaluShop.dto.OrderRequest;
import pl.galushop.GaluShop.entity.Order;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.exception.OrderNotFoundException;
import pl.galushop.GaluShop.exception.UserNotFoundException;
import pl.galushop.GaluShop.repository.OrderRepository;
import pl.galushop.GaluShop.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductService productService;
    private final MessageService messageService;

    public Order getOrder(Long orderId){
        if(orderId == null || orderId < 0){
            throw new IllegalArgumentException(messageService.getMessage("error.invalidOrderId", orderId));
        }
        return orderRepository.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException(messageService.getMessage("error.orderNotFound", orderId)));
    }

    public void saveOrderToDatabase(OrderRequest orderRequest) {
        Order order = new Order();
        setOrderFields(orderRequest, order);
        orderRepository.save(order);
    }

    public List<Order> getAllOrdersByUser(Long userId) {
        if (userId == null || userId < 0){
            throw new IllegalArgumentException(messageService.getMessage("error.invalidUserId", userId));
        }
        userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(messageService.getMessage("error.userNotFound", userId)));
        return orderRepository.findAllByUser_UserId(userId).orElseThrow(
                () -> new OrderNotFoundException(messageService.getMessage("error.orderNotFoundByUserId", userId)));
    }

    public Order getSpecificOrder(Long userId) {
        if (userId == null || userId < 0) {
            throw new IllegalArgumentException(messageService.getMessage("error.orderNotFoundByUserId", userId));
        }
        userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(messageService.getMessage("error.userNotFound", userId)));
        return orderRepository.findByUser_UserId(userId).orElseThrow(
                () -> new OrderNotFoundException(messageService.getMessage("error.orderNotFoundByUserId", userId)));

    }

    public void deleteOrder(Long orderId){
        if(orderId == null || orderId < 0) {
            throw new IllegalArgumentException(messageService.getMessage("error.invalidOrderId", orderId));
        }
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException(messageService.getMessage("error.orderNotFound", orderId)));
        orderRepository.delete(order);
    }

    public void updateOrder(OrderRequest orderRequest){
        if(orderRequest.getOrderId() == null || orderRequest.getOrderId() < 0){
            throw new IllegalArgumentException(messageService.getMessage("error.invalidOrderId", orderRequest.getOrderId()));
        }
        Order existingOrder = orderRepository.findById(orderRequest.getOrderId()).orElseThrow(
                () -> new OrderNotFoundException(messageService.getMessage("error.orderNotFound", orderRequest.getOrderId())));
        setOrderFields(orderRequest, existingOrder);
        orderRepository.save(existingOrder);
    }

    private void setOrderFields(OrderRequest orderRequest, Order order) {
        List<Product> products = orderRequest.getProductIds().stream()
                .map(productService::getProductById)
                .collect(Collectors.toList());
        order.setLocalDateTime(orderRequest.getLocalDateTime());
        order.setStatus(orderRequest.getOrderStatus());
        order.setUser(order.getUser());
        order.setProducts(products);
    }
}
