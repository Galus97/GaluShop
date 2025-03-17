package pl.galushop.GaluShop.dto.response;

import pl.galushop.GaluShop.entity.WarehouseProduct;

public record WarehouseProductResponse(Long warehouseProductId, Long productId, Integer quantity) {

    public static WarehouseProductResponse fromEntity(WarehouseProduct warehouseProduct){
        return new WarehouseProductResponse(
                warehouseProduct.getWarehouseProductId(),
                warehouseProduct.getProduct().getProductId(),
                warehouseProduct.getQuantity()
        );
    }
}
