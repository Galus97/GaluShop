package pl.galushop.GaluShop.dto;

import lombok.Data;

@Data
public class WarehouseProductRequest {
    private Long productId;
    private Integer quantity;
}
