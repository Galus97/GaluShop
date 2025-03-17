package pl.galushop.GaluShop.dto.request;

import lombok.Data;

@Data
public class WarehouseProductRequest {
    private Long warehouseProductId;
    private Long productId;
    private Integer quantity;
}
