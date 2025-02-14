package pl.galushop.GaluShop.controller.warehouse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.WarehouseProductRequest;
import pl.galushop.GaluShop.entity.WarehouseProduct;
import pl.galushop.GaluShop.service.WarehouseProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/warehouse")
public class SaveWarehouseProductController {
    private final WarehouseProductService warehouseProductService;

    @PutMapping("/save")
    public ResponseEntity<WarehouseProduct> addProductToWarehouse(@RequestBody WarehouseProductRequest warehouseProductRequest) {
        warehouseProductService.addProductToWarehouse(warehouseProductRequest);
        return ResponseEntity.ok(warehouseProductService.getWarehouseProduct(warehouseProductRequest.getProductId()));
    }
}
