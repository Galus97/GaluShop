package pl.galushop.GaluShop.mapper;

import org.springframework.stereotype.Component;
import pl.galushop.GaluShop.dto.response.WarehouseProductResponse;
import pl.galushop.GaluShop.model.WarehouseProduct;

@Component
public class WarehouseProductMapper {

    public static WarehouseProductResponse toWarehouseProductResponse(WarehouseProduct warehouseProduct) {
        return new WarehouseProductResponse(
                warehouseProduct.getWarehouseProductId(),
                warehouseProduct.getProduct().getProductId(),
                warehouseProduct.getQuantity()
        );
    }
}
