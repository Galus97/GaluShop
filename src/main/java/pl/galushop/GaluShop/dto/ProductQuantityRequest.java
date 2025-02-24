package pl.galushop.GaluShop.dto;

import lombok.Data;

@Data
public class ProductQuantityRequest {
    private Long productId;
    private int quantity;
}
