package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.entity.WarehouseProduct;
import pl.galushop.GaluShop.repository.ProductRepository;
import pl.galushop.GaluShop.repository.WarehouseProductRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class WarehouseProductService {
    private final WarehouseProductRepository warehouseRepository;
    private final ProductRepository productRepository;

    public void addProductToWarehouse(WarehouseProduct warehouseProduct) {
        if (warehouseProduct != null) {
            warehouseRepository.save(warehouseProduct);
        }
    }

    public List<WarehouseProduct> getAllProductInWarehouse() {
        return warehouseRepository.findAll();
    }

    public void deleteWarehouseProduct(Long warehouseId) {
        if (warehouseId != null && warehouseId > 0) {
            warehouseRepository.deleteById(warehouseId);
        }
    }

    public WarehouseProduct getProductInfoInWarehouse(Long productId) {
        if (productId != null && productId > 0) {
            if (productRepository.findByProductId(productId).isPresent()) {
                return warehouseRepository.findByProduct_ProductId(productId);
            }
            throw new NoSuchElementException("That product doesn't exist in database");
        }
        throw new IllegalArgumentException("Product Id is invalid");
    }

    public void updateQuantityByProductId(Long productId, Integer quantity) {
        if (productId != null && productId > 0 && quantity != null && quantity >= 0) {
            if (productRepository.findByProductId(productId).isPresent()) {
                warehouseRepository.updateQuantityByProductId(productId, quantity);
            } else {
                throw new NoSuchElementException("That product doesn't exist in database");
            }
        } else {
            throw new IllegalArgumentException("Product Id is invalid");
        }
    }
}
