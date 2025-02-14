package pl.galushop.GaluShop.controller.warehouse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.entity.WarehouseProduct;
import pl.galushop.GaluShop.service.WarehouseProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/warehouseProduct")
public class ShowWarehouseProductController {
    private final WarehouseProductService warehouseProductService;

    @GetMapping("/show/{id}")
    public ResponseEntity<WarehouseProduct> showInfoWarehouseProduct(@PathVariable Long id){
        return ResponseEntity.ok(warehouseProductService.getWarehouseProduct(id));
    }
}
