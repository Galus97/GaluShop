package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.util.ServiceValidator;
import pl.galushop.GaluShop.component.ErrorMessages;
import pl.galushop.GaluShop.dto.response.OrderProductResponse;
import pl.galushop.GaluShop.model.OrderItem;
import pl.galushop.GaluShop.repository.OrderProductRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class responsible for managing order product operations.
 * It handles creating and retrieving order-product relationships.
 */
@Service
@RequiredArgsConstructor
public class OrderProductService {
    private final OrderProductRepository orderProductRepository;
    private final ServiceValidator serviceValidator;

    /**
     * Saves an {@link OrderItem} entity to the database.
     *
     * @param orderItem The {@link OrderItem} entity to be saved.
     * @return A response DTO representing the saved order product.
     * @throws IllegalArgumentException If the orderProduct is {@code null}.
     */
    public OrderProductResponse saveOrderProduct(OrderItem orderItem) {
        serviceValidator.throwIfRequestIsNull(orderItem, ErrorMessages.ORDER_PRODUCT_IS_NULL);
        return OrderProductResponse.fromEntity(orderProductRepository.save(orderItem));
    }

    /**
     * Retrieves all order products associated with a specific order ID.
     *
     * @param orderId The ID of the order.
     * @return A list of response DTOs representing the products in the order.
     * @throws IllegalArgumentException If the order ID is {@code null} or invalid.
     */
    public List<OrderProductResponse> getOrderProductsByOrderId(Long orderId) {
        serviceValidator.throwIfIdIsNotValid(orderId, ErrorMessages.INVALID_ORDER_ID);
        return orderProductRepository.findByOrder_OrderId(orderId)
                .stream()
                .map(OrderProductResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all order products associated with a specific product ID.
     *
     * @param productId The ID of the product.
     * @return A list of response DTOs representing the orders containing the product.
     * @throws IllegalArgumentException If the product ID is {@code null} or invalid.
     */
    public List<OrderProductResponse> getOrderProductsByProductId(Long productId) {
        serviceValidator.throwIfIdIsNotValid(productId, ErrorMessages.INVALID_ORDER_ID);
        return orderProductRepository.findByProduct_ProductId(productId)
                .stream()
                .map(OrderProductResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
