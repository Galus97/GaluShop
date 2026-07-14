package pl.galushop.GaluShop.dto.response;

import pl.galushop.GaluShop.component.OrderStatus;
import pl.galushop.GaluShop.model.Order;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(Long orderId, LocalDateTime localDateTime, OrderStatus status, Long userId,
                            List<OrderProductResponse> products) {
    public static OrderResponse fromEntity(Order order) {
        return new OrderResponse(
                order.getOrderId(),
                order.getLocalDateTime(),
                order.getStatus(),
                order.getUser().getUserId(),
                order.getOrderProducts().stream().map(OrderProductResponse::fromEntity).toList()
        );
    }
}
