package pl.galushop.GaluShop.controller.warehouse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.WarehouseProductRequest;
import pl.galushop.GaluShop.entity.WarehouseProduct;
import pl.galushop.GaluShop.service.WarehouseProductService;

@RestController
@RequiredArgsConstructor
public class WarehouseProductInfoController {
    private final WarehouseProductService warehouseProductService;

    @GetMapping("/showInfoWarehouseProduct")
    public WarehouseProduct showInfoWarehouseProduct(@RequestBody WarehouseProductRequest warehouseProductRequest){
        return warehouseProductService.getProductInfoInWarehouse(warehouseProductRequest.getProductId());
    }
}
