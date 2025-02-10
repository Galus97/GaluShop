package pl.galushop.GaluShop.controller.warehouse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.WarehouseProductRequest;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.entity.WarehouseProduct;
import pl.galushop.GaluShop.service.ProductService;
import pl.galushop.GaluShop.service.WarehouseProductService;

@RestController
@RequiredArgsConstructor
public class WarehouseProductController {
    private final WarehouseProductService warehouseProductService;

    @GetMapping("/warehouse/addProduct")
    public String addProductToWarehouse(@RequestBody WarehouseProductRequest warehouseProductRequest) {
        warehouseProductService.addProductToWarehouse(warehouseProductRequest);
        return "success";
    }
}
