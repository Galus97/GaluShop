package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.galushop.GaluShop.component.ErrorMessages;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.dto.request.OrderRequest;
import pl.galushop.GaluShop.dto.response.OrderResponse;
import pl.galushop.GaluShop.dto.request.ProductQuantityRequest;
import pl.galushop.GaluShop.entity.Order;
import pl.galushop.GaluShop.entity.OrderProduct;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.entity.User;
import pl.galushop.GaluShop.exception.OrderNotFoundException;
import pl.galushop.GaluShop.exception.ProductNotFoundException;
import pl.galushop.GaluShop.exception.UserNotFoundException;
import pl.galushop.GaluShop.repository.OrderRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class responsible for managing order operations.
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final MessageService messageService;
    private final ProductService productService;
    private final OrderProductService orderProductService;

    /**
     * Retrieves an order by its ID.
     *
     * @param orderId The ID of the order to retrieve.
     * @return The retrieved order entity.
     * @throws IllegalArgumentException if the order ID is null or invalid.
     * @throws OrderNotFoundException   if no order is found with the given ID.
     */
    public OrderResponse getOrderResponse(Long orderId) {
        throwIfIdIsInvalid(orderId, ErrorMessages.INVALID_ORDER_ID);
        return OrderResponse.fromEntity(getOrderOrThrowIfNotExist(orderId));
    }

    public Order getOrderEntity(Long orderId) {
        throwIfIdIsInvalid(orderId, ErrorMessages.INVALID_ORDER_ID);
        return getOrderOrThrowIfNotExist(orderId);
    }


    /**
     * Saves a new order to the database.
     * Saves a new orderProduct to the database.
     *
     * @param orderRequest The request object containing order details.
     * @return The created Order
     * @throws UserNotFoundException    if the user associated with the order is not found.
     * @throws ProductNotFoundException if any product in the order is not found.
     */

    @Transactional
    public OrderResponse saveOrder(OrderRequest orderRequest) {
        Order order = buildOrder(orderRequest);
        return OrderResponse.fromEntity(orderRepository.save(order));
    }

    /**
     * Retrieves all orders made by a specific user.
     *
     * @param userId The ID of the user.
     * @return A list of orders associated with the user.
     * @throws IllegalArgumentException if the user ID is null or invalid.
     * @throws UserNotFoundException    if the user is not found.
     * @throws OrderNotFoundException   if no orders are found for the user.
     */
    public List<OrderResponse> getAllOrdersByUser(Long userId) {
        throwIfIdIsInvalid(userId, ErrorMessages.INVALID_USER_ID);

        //Throws exception if user doesn't exist in database
        userService.throwIfUserDoesntExist(userId);

        return orderRepository.findAllByUser_UserId(userId)
                .stream()
                .map(OrderResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Deletes an order by its ID.
     *
     * @param orderId The ID of the order to delete.
     * @throws IllegalArgumentException if the order ID is null or invalid.
     * @throws OrderNotFoundException   if no order is found with the given ID.
     */
    public void deleteOrder(Long orderId) {
        throwIfIdIsInvalid(orderId, ErrorMessages.INVALID_ORDER_ID);

        orderRepository.delete(getOrderOrThrowIfNotExist(orderId));
    }

    /**
     * Updates an existing order's details.
     *
     * @param orderRequest The request object containing updated order details.
     * @throws IllegalArgumentException if the request object contains an invalid order ID.
     * @throws OrderNotFoundException   if no order is found with the given ID.
     * @throws UserNotFoundException    if the user associated with the order is not found.
     * @throws ProductNotFoundException if any product in the order is not found.
     */
    @Transactional
    public OrderResponse updateOrder(OrderRequest orderRequest) {
        throwIfIdIsInvalid(orderRequest.getOrderId(), ErrorMessages.INVALID_ORDER_ID);

        Order existingOrder = getOrderOrThrowIfNotExist(orderRequest.getOrderId());
        Order updatedOrder = buildOrder(orderRequest);
        updatedOrder.setOrderId(existingOrder.getOrderId());

        return OrderResponse.fromEntity(orderRepository.save(updatedOrder));
    }

    /**
     * Builds an Order entity from the given OrderRequest.
     * Saves a new orderProduct to the database.
     *
     * @param orderRequest The request object containing order details.
     * @return The constructed Order entity.
     * @throws UserNotFoundException    if the user associated with the order is not found.
     * @throws ProductNotFoundException if any product in the order is not found.
     */
    private Order buildOrder(OrderRequest orderRequest) {
        User user = userService.getUser(orderRequest.getUserId());

        Map<Long, Product> productsMap = productService.getAllProductByIds(
                orderRequest.getProductQuantityRequests().stream()
                        .map(ProductQuantityRequest::getProductId)
                        .toList()
        ).stream().collect(Collectors.toMap(Product::getProductId, product -> product));

        List<OrderProduct> orderProducts = orderRequest.getProductQuantityRequests().stream()
                .map(pq -> {
                    Product product = productsMap.get(pq.getProductId());
                    if (product == null) {
                        throw new ProductNotFoundException(
                                messageService.getMessage(ErrorMessages.PRODUCT_NOT_FOUND, pq.getProductId())
                        );
                    }
                    return OrderProduct.builder()
                            .product(product)
                            .quantity(pq.getQuantity())
                            .build();
                }).toList();

        Order order = Order.builder()
                .localDateTime(orderRequest.getLocalDateTime())
                .status(orderRequest.getOrderStatus())
                .user(user)
                .orderProducts(orderProducts)
                .build();

        orderProducts.forEach(op -> op.setOrder(order));

        return order;
    }


    private void throwIfIdIsInvalid(Long id, String message) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(messageService.getMessage(message, id));
        }
    }

    private Order getOrderOrThrowIfNotExist(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException(messageService.getMessage(ErrorMessages.ORDER_NOT_FOUND, orderId)));
    }
}
