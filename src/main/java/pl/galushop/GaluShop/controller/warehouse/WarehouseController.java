package pl.galushop.GaluShop.controller.warehouse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
public class WarehouseController {
    private final WarehouseProductService warehouseProductService;

    @GetMapping("/{id}")
    public ResponseEntity<WarehouseProduct> showWarehouseProduct(@PathVariable Long id){
        return ResponseEntity.ok(warehouseProductService.getWarehouseProduct(id));
    }

    @PostMapping
    public ResponseEntity<WarehouseProduct> saveWarehouseProduct(@RequestBody WarehouseProductRequest warehouseProductRequest) {
        warehouseProductService.addProductToWarehouse(warehouseProductRequest);
        return ResponseEntity.ok(warehouseProductService.getWarehouseProduct(warehouseProductRequest.getProductId()));
    }

    @PutMapping("/{id}/{quantity}")
    public ResponseEntity<WarehouseProduct> updateQuantity(@PathVariable Long id, @PathVariable Integer quantity){
        warehouseProductService.updateQuantityByProductId(id, quantity);
        return ResponseEntity.ok(warehouseProductService.getWarehouseProduct(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWarehouseProduct(@PathVariable Long id){
        warehouseProductService.deleteWarehouseProduct(id);
        return ResponseEntity.noContent().build();
    }
}
