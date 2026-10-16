package pl.galushop.GaluShop.dto.request;

import lombok.Data;

@Data
public class ItemQuantityRequest {
    private Long productId;
    private int quantity;
}
