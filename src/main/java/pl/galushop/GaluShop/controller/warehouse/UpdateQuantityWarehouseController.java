package pl.galushop.GaluShop.controller.warehouse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.entity.WarehouseProduct;
import pl.galushop.GaluShop.service.WarehouseProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/warehouse")
public class UpdateQuantityWarehouseController {
    private final WarehouseProductService warehouseProductService;
    @PutMapping("/update/{id}/{quantity}")
    public ResponseEntity<WarehouseProduct> updateQuantity(@PathVariable Long id, @PathVariable Integer quantity){
        warehouseProductService.updateQuantityByProductId(id, quantity);
        return ResponseEntity.ok(warehouseProductService.getWarehouseProduct(id));
    }
}
