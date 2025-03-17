package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.galushop.GaluShop.component.ErrorMessages;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.dto.request.WarehouseProductRequest;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.entity.WarehouseProduct;
import pl.galushop.GaluShop.exception.ProductNotFoundException;
import pl.galushop.GaluShop.exception.WarehouseProductNotFoundException;
import pl.galushop.GaluShop.repository.WarehouseProductRepository;

import java.util.List;

/**
 * Service class responsible for managing warehouse products operations.
 */
@Service
@RequiredArgsConstructor
public class WarehouseProductService {
    private final WarehouseProductRepository warehouseRepository;
    private final ProductService productService;
    private final MessageService messageService;

    /**
     * Retrieves a warehouse product by its product ID.
     *
     * @param productId The ID of the product in the warehouse.
     * @return The corresponding warehouse product.
     * @throws IllegalArgumentException If the provided ID is null or negative.
     * @throws WarehouseProductNotFoundException If no warehouse product is found.
     */
    public WarehouseProduct getWarehouseProductEntity(Long productId) {
        throwIfIdIsInvalid(productId, ErrorMessages.INVALID_PRODUCT_ID);
        return getWarehouseProductOrThrow(productId, ErrorMessages.WAREHOUSE_NOT_FOUND_BY_PRODUCT_ID);
    }


    /**
     * Adds a new product to the warehouse.
     *
     * @param warehouseProductRequest The request containing product ID and quantity.
     * @return The created WarehouseProduct
     * @throws IllegalArgumentException If the request is null or contains invalid fields.
     * @throws ProductNotFoundException If the specified product is not found.
     */
    @Transactional
    public WarehouseProduct saveWarehouseProduct(WarehouseProductRequest warehouseProductRequest) {
        return warehouseRepository.save(buildWarehouseProduct(warehouseProductRequest));
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
        throwIfIdIsInvalid(warehouseId, ErrorMessages.WAREHOUSE_ID_IS_INVALID);
        WarehouseProduct warehouseProduct = getWarehouseProductOrThrow(warehouseId, ErrorMessages.WAREHOUSE_NOT_FOUND);

        warehouseRepository.delete(warehouseProduct);
    }

    /**
     * Updates an existing warehouse product based on the provided request.
     *
     * @param warehouseProductRequest The request containing updated product details.
     * @throws WarehouseProductNotFoundException If the warehouse product is not found.
     */
    @Transactional
    public void updateWarehouseProduct(WarehouseProductRequest warehouseProductRequest){
        WarehouseProduct existingWarehouseProduct = getWarehouseProductOrThrow(warehouseProductRequest.getProductId(),
                ErrorMessages.WAREHOUSE_NOT_FOUND);

        Product product = productService.getProduct(warehouseProductRequest.getProductId());
        existingWarehouseProduct.setProduct(product);
        existingWarehouseProduct.setQuantity(warehouseProductRequest.getQuantity());

        warehouseRepository.save(existingWarehouseProduct);
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
        throwIfIdIsInvalid(productId, ErrorMessages.INVALID_PRODUCT_ID);
        if(quantity == null || quantity < 0){
            throw new IllegalArgumentException(messageService.getMessage(ErrorMessages.INVALID_QUANTITY));
        }
        WarehouseProduct existingWarehouseProduct = warehouseRepository.findByProduct_ProductId(productId)
                .orElseThrow(() -> new WarehouseProductNotFoundException(messageService.getMessage(ErrorMessages.WAREHOUSE_IS_NULL)));

        existingWarehouseProduct.setQuantity(quantity);
        warehouseRepository.save(existingWarehouseProduct);
    }

    /**
     * Builds a WarehouseProduct entity from the given request.
     *
     * @param warehouseProductRequest The request containing product ID and quantity.
     * @return A new WarehouseProduct instance.
     * @throws IllegalArgumentException If the request is invalid.
     * @throws ProductNotFoundException If the product is not found.
     */
    private WarehouseProduct buildWarehouseProduct(WarehouseProductRequest warehouseProductRequest) {
        throwIfRequestIsInvalid(warehouseProductRequest);

        Product product = productService.getProduct(warehouseProductRequest.getProductId());
        return WarehouseProduct.builder()
                .warehouseProductId(null)
                .product(product)
                .quantity(warehouseProductRequest.getQuantity())
                .build();
    }

    private void throwIfIdIsInvalid(Long id, String message){
        if(id == null || id <= 0){
            throw new IllegalArgumentException(messageService.getMessage(message, id));
        }
    }

    private void throwIfRequestIsInvalid(WarehouseProductRequest warehouseProductRequest){
        if (warehouseProductRequest == null) {
            throw new IllegalArgumentException(messageService.getMessage(ErrorMessages.WAREHOUSE_IS_NULL));
        }
        if(warehouseProductRequest.getProductId() == null || warehouseProductRequest.getProductId() < 0
                || warehouseProductRequest.getQuantity() == null  || warehouseProductRequest.getQuantity() < 0){
            throw new IllegalArgumentException(messageService.getMessage(ErrorMessages.INVALID_FIELDS_IN_REQUEST));
        }
    }

    private WarehouseProduct getWarehouseProductOrThrow(Long productId, String message) {
        return warehouseRepository.findById(productId)
                .orElseThrow(() -> new WarehouseProductNotFoundException(messageService.getMessage(message, productId)));
    }
}
