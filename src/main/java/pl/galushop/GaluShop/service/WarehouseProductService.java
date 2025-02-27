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

/**
 * Service class responsible for managing warehouse products operations.
 */
@Service
@RequiredArgsConstructor
public class WarehouseProductService {
    private final WarehouseProductRepository warehouseRepository;
    private final ProductRepository productRepository;
    private final MessageService messageService;

    /**
     * Retrieves a warehouse product by its product ID.
     *
     * @param productId The ID of the product in the warehouse.
     * @return The corresponding warehouse product.
     * @throws IllegalArgumentException If the provided ID is null or negative.
     * @throws WarehouseProductNotFoundException If no warehouse product is found.
     */
    public WarehouseProduct getWarehouseProduct(Long productId) {
        if (productId == null || productId < 0) {
            throw new IllegalArgumentException(messageService.getMessage("error.invalidProductId", productId));
        }
        return warehouseRepository.findById(productId)
                .orElseThrow(() -> new WarehouseProductNotFoundException(messageService.getMessage("error.warehouseProductByProductIdNotFound", productId)));
    }

    /**
     * Adds a new product to the warehouse.
     *
     * @param warehouseProductRequest The request containing product ID and quantity.
     * @throws IllegalArgumentException If the request is null or contains invalid fields.
     * @throws ProductNotFoundException If the specified product is not found.
     */
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

    /**
     * Retrieves all products currently in the warehouse.
     *
     * @return A list of all warehouse products.
     */
    public List<WarehouseProduct> getAllProductInWarehouse() {
        return warehouseRepository.findAll();
    }

    /**
     * Deletes a warehouse product by its ID.
     *
     * @param warehouseId The ID of the warehouse product.
     * @throws IllegalArgumentException If the ID is null or negative.
     * @throws WarehouseProductNotFoundException If the warehouse product is not found.
     */
    public void deleteWarehouseProduct(Long warehouseId) {
        if (warehouseId == null || warehouseId < 0) {
            throw new IllegalArgumentException(messageService.getMessage("error.invalidWarehouseProductId", warehouseId));
        }
        WarehouseProduct warehouseProduct = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new WarehouseProductNotFoundException(messageService.getMessage("error.warehouseProductNotFound", warehouseId)));

        warehouseRepository.delete(warehouseProduct);
    }

    /**
     * Updates the quantity of a product in the warehouse based on the product ID.
     *
     * @param productId The ID of the product.
     * @param quantity The new quantity of the product.
     * @throws IllegalArgumentException If the product ID or quantity is invalid.
     * @throws WarehouseProductNotFoundException If the warehouse product is not found.
     */
    @Transactional
    public void updateQuantityByProductId(Long productId, Integer quantity) {
        if(productId == null || productId < 0){
            throw new IllegalArgumentException(messageService.getMessage("error.invalidProductId", productId));
        }
        if(quantity == null || quantity < 0){
            throw new IllegalArgumentException(messageService.getMessage("error.invalidQuantity"));
        }
        WarehouseProduct existingWarehouseProduct = warehouseRepository.findByProduct_ProductId(productId)
                .orElseThrow(() -> new WarehouseProductNotFoundException(messageService.getMessage("error.warehouseProductIsNull")));

        existingWarehouseProduct.setQuantity(quantity);
        warehouseRepository.save(existingWarehouseProduct);
    }
}
