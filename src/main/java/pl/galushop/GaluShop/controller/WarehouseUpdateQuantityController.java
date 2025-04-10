package pl.galushop.GaluShop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.response.WarehouseProductResponse;
import pl.galushop.GaluShop.service.WarehouseProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/warehouse")
public class WarehouseUpdateQuantityController {
    private final WarehouseProductService warehouseProductService;

    @PutMapping("/{id}/{quantity}")
    public ResponseEntity<WarehouseProductResponse> updateQuantity(@PathVariable Long id, @PathVariable Integer quantity) {
        return ResponseEntity.ok(warehouseProductService.updateQuantityByProductId(id, quantity));
    }
}
