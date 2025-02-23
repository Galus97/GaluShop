package pl.galushop.GaluShop.dto;

import lombok.Data;
import pl.galushop.GaluShop.component.OrderStatus;
import pl.galushop.GaluShop.entity.OrderProductId;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderRequest {
    private Long orderId;
    private Long userId;
    private LocalDateTime localDateTime;
    private OrderStatus orderStatus;
    private List<OrderProductId> orderProductsId;
}
