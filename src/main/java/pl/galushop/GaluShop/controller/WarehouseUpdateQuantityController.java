package pl.galushop.GaluShop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.service.WarehouseProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/warehouse")
public class WarehouseUpdateQuantityController {
    private final WarehouseProductService warehouseProductService;
}
