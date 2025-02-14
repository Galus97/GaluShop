package pl.galushop.GaluShop.controller.warehouse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.galushop.GaluShop.dto.WarehouseProductRequest;
import pl.galushop.GaluShop.service.WarehouseProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/warehouseProduct")
public class DeleteWarehouseProductController {
    private final WarehouseProductService warehouseProductService;

    @DeleteMapping("/delete/{id}")
    public String deleteWarehouseProduct(@RequestBody WarehouseProductRequest warehouseProductRequest){
        Long warehouseProductId = warehouseProductRequest.getWarehouseProductId();
        if(warehouseProductId != null){
            warehouseProductService.deleteWarehouseProduct(warehouseProductId);
            return "Success";
        }
        return "Warehouse Product Id is required";
    }
}
