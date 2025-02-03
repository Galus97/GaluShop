package pl.galushop.GaluShop.controller.warehouse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.WarehouseProductRequest;
import pl.galushop.GaluShop.service.WarehouseProductService;

@RestController
@RequiredArgsConstructor
public class UpdateQuantityWarehouseController {
    private final WarehouseProductService warehouseProductService;
    @GetMapping("/updateQuantity")
    public String updateQuantity(@RequestBody WarehouseProductRequest warehouseProductRequest){
        warehouseProductService.updateQuantityByProductId(warehouseProductRequest.getProductId(),
                warehouseProductRequest.getQuantity());
        return "Success";
    }
}
