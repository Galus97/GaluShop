package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.dto.WarehouseProductRequest;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.entity.WarehouseProduct;
import pl.galushop.GaluShop.exception.ProductNotFoundException;
import pl.galushop.GaluShop.exception.WarehouseProductNotFoundException;
import pl.galushop.GaluShop.repository.ProductRepository;
import pl.galushop.GaluShop.repository.WarehouseProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseProductService {
    private final WarehouseProductRepository warehouseRepository;
    private final ProductRepository productRepository;
    private final MessageService messageService;

    public WarehouseProduct getWarehouseProduct(Long productId) {
        if (productId == null && productId < 0) {
            throw new IllegalArgumentException(messageService.getMessage("error.invalidProductId", productId));
        }
        return warehouseRepository.findById(productId)
                .orElseThrow(() -> new WarehouseProductNotFoundException(messageService.getMessage("error.warehouseProductByProductIdNotFound", productId)));
    }

    public void addProductToWarehouse(WarehouseProductRequest warehouseProductRequest) {
        if (warehouseProductRequest == null) {
            throw new IllegalArgumentException(messageService.getMessage("error.warehouseProductIsNull"));
        }
        if(warehouseProductRequest.getProductId() == null && warehouseProductRequest.getQuantity() < 0){
            throw new IllegalArgumentException(messageService.getMessage("error.invalidFieldsWarehouseProduct"));
        }

        WarehouseProduct warehouseProduct = new WarehouseProduct();
        Product product = productRepository.findByProductId(warehouseProductRequest.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(messageService.getMessage("error.productNotFound", warehouseProductRequest.getProductId())));

        warehouseProduct.setProduct(product);
        warehouseProduct.setQuantity(warehouseProductRequest.getQuantity());
        warehouseRepository.save(warehouseProduct);
    }

    public List<WarehouseProduct> getAllProductInWarehouse() {
        return warehouseRepository.findAll();
    }

    public void deleteWarehouseProduct(Long warehouseId) {
        if (warehouseId == null && warehouseId < 0) {
            throw new IllegalArgumentException(messageService.getMessage("error.invalidWarehouseProductId", warehouseId));
        }
        WarehouseProduct warehouseProduct = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new WarehouseProductNotFoundException(messageService.getMessage("error.warehouseProductNotFound", warehouseId)));

        warehouseRepository.delete(warehouseProduct);
    }

    @Transactional
    public void updateQuantityByProductId(Long productId, Integer quantity) {
        if(productId == null && productId < 0){
            throw new IllegalArgumentException(messageService.getMessage("error.invalidProductId", productId));
        }
        if(quantity == null && quantity < 0){
            throw new IllegalArgumentException(messageService.getMessage("error.invalidQuantity"));
        }
        WarehouseProduct existingWarehouseProduct = warehouseRepository.findByProduct_ProductId(productId)
                .orElseThrow(() -> new WarehouseProductNotFoundException(messageService.getMessage("error.warehouseProductIsNull")));

        existingWarehouseProduct.setQuantity(quantity);
        warehouseRepository.save(existingWarehouseProduct);
    }
}
