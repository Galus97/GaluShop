package pl.galushop.GaluShop.dto.response;

import pl.galushop.GaluShop.component.OrderStatus;
import pl.galushop.GaluShop.model.Order;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long orderId,
        LocalDateTime createdAt,
        OrderStatus status,
        Long userId,
        List<OrderItemResponse> products) {

    public static OrderResponse fromEntity(Order order) {
        return new OrderResponse(
                order.getOrderId(),
                order.getCreatedAt(),
                order.getStatus(),
                order.getUser().getUserId(),
                order.getOrderItems().stream().map(OrderItemResponse::fromEntity).toList()
        );
    }
}
