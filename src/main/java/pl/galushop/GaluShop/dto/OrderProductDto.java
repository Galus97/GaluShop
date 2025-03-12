package pl.galushop.GaluShop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.galushop.GaluShop.entity.OrderProduct;

@Data
@AllArgsConstructor
public class OrderProductDto {
    private Long orderId;
    private Long productId;
    private int quantity;

    public static OrderProductDto fromEntity(OrderProduct orderProduct) {
        return new OrderProductDto(
                orderProduct.getOrder().getOrderId(),
                orderProduct.getProduct().getProductId(),
                orderProduct.getQuantity()
        );
    }
}
