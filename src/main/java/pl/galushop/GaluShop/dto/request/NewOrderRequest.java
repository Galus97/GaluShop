package pl.galushop.GaluShop.dto.request;

import lombok.Builder;
import lombok.Data;
import pl.galushop.GaluShop.component.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class NewOrderRequest {
    private Long userId;
    private LocalDateTime createdAt;
    private OrderStatus orderStatus;
    private List<ItemQuantityRequest> itemQuantityRequests;
}
