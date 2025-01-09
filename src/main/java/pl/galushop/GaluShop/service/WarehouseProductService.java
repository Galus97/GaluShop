package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.entity.WarehouseProduct;
import pl.galushop.GaluShop.repository.WarehouseProductRepository;

@Service
@RequiredArgsConstructor
public class WarehouseProductService {
    private final WarehouseProductRepository warehouseRepository;

    public void addProductToWarehouse(WarehouseProduct warehouseProduct){
        if(warehouseProduct != null){
            warehouseRepository.save(warehouseProduct);
        }
    }
}
