package pl.galushop.GaluShop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.entity.WarehouseProduct;
import pl.galushop.GaluShop.service.WarehouseProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AllWarehouseProductController {
    private final WarehouseProductService warehouseProductService;


    @GetMapping("/allProduct")
    public List<WarehouseProduct> showAllProductInWarehouse(){
        return warehouseProductService.getAllProductInWarehouse();
    }
}
