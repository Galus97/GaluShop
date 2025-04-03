package pl.galushop.GaluShop.controller;

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
import pl.galushop.GaluShop.dto.request.WarehouseProductRequest;
import pl.galushop.GaluShop.dto.response.WarehouseProductResponse;
import pl.galushop.GaluShop.service.WarehouseProductService;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/warehouse")
public class WarehouseController {
    private final WarehouseProductService warehouseProductService;

    @GetMapping("/{id}")
    public ResponseEntity<WarehouseProductResponse> showWarehouseProduct(@PathVariable Long id){
        return ResponseEntity.ok(warehouseProductService.getWarehouseProductResponse(id));
    }

    @PostMapping
    public ResponseEntity<WarehouseProductResponse> saveWarehouseProduct(@RequestBody WarehouseProductRequest warehouseProductRequest) {
        WarehouseProductResponse savedWarehouseProduct = warehouseProductService.saveWarehouseProduct(warehouseProductRequest);
        return ResponseEntity.created(URI.create("/warehouse/" + savedWarehouseProduct.warehouseProductId()))
                .body(savedWarehouseProduct);
    }

    @PutMapping
    public ResponseEntity<WarehouseProductResponse> updateWarehouseProduct(@RequestBody WarehouseProductRequest warehouseProductRequest){
        return ResponseEntity.ok(warehouseProductService.updateWarehouseProduct(warehouseProductRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWarehouseProduct(@PathVariable Long id){
        warehouseProductService.deleteWarehouseProduct(id);
        return ResponseEntity.noContent().build();
    }
}
