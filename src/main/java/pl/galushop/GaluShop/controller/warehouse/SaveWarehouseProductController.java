package pl.galushop.GaluShop.controller.warehouse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.WarehouseProductRequest;
import pl.galushop.GaluShop.service.WarehouseProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/warehouse")
public class SaveWarehouseProductController {
    private final WarehouseProductService warehouseProductService;

    @PutMapping("/addProduct")
    public String addProductToWarehouse(@RequestBody WarehouseProductRequest warehouseProductRequest) {
        warehouseProductService.addProductToWarehouse(warehouseProductRequest);
        return "success";
    }
}
