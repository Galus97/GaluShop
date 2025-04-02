package pl.galushop.GaluShop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.response.WarehouseProductResponse;
import pl.galushop.GaluShop.service.WarehouseProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/warehouse")
public class WarehouseAllProductController {
    private final WarehouseProductService warehouseProductService;


    @GetMapping("/allProduct")
    public ResponseEntity<List<WarehouseProductResponse>> showAllProductInWarehouse(){
        return ResponseEntity.ok(warehouseProductService.getAllProductInWarehouse());
    }
}
