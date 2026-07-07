package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.galushop.GaluShop.component.ErrorMessages;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.dto.request.WarehouseProductRequest;
import pl.galushop.GaluShop.dto.response.WarehouseProductResponse;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.entity.WarehouseProduct;
import pl.galushop.GaluShop.exception.ProductNotFoundException;
import pl.galushop.GaluShop.exception.WarehouseProductNotFoundException;
import pl.galushop.GaluShop.repository.WarehouseProductRepository;
import pl.galushop.GaluShop.util.ServiceValidator;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class responsible for managing warehouse product operations.
 * It handles creating, reading, updating, and deleting (CRUD) warehouse product data,
 * as well as managing product quantities.
 */
@Service
@RequiredArgsConstructor
public class WarehouseProductService {
    private final WarehouseProductRepository warehouseRepository;
    private final ProductService productService;
    private final MessageService messageService;
    private final ServiceValidator serviceValidator;
    /**
     * Retrieves a warehouse product entity by its product ID.
     *
     * @param productId The ID of the product in the warehouse.
     * @return The corresponding WarehouseProduct entity.
     * @throws IllegalArgumentException          If the product ID is null or invalid.
     * @throws WarehouseProductNotFoundException If no warehouse product is found for the given ID.
     */
    public WarehouseProduct getWarehouseProductEntityByProductId(Long productId) {
        serviceValidator.throwIfIdIsNotValid(productId, ErrorMessages.INVALID_PRODUCT_ID);
        return getWarehouseProductByProductIdOrThrowIfNotExist(productId, ErrorMessages.WAREHOUSE_NOT_FOUND_BY_PRODUCT_ID);
    }

    /**
     * Retrieves a warehouse product as a response DTO by its product ID.
     *
     * @param productId The ID of the product in the warehouse.
     * @return A response DTO representing the warehouse product.
     * @throws IllegalArgumentException          If the product ID is null or invalid.
     * @throws WarehouseProductNotFoundException If the warehouse product is not found.
     */
    public WarehouseProductResponse getWarehouseProductResponse(Long productId) {
        serviceValidator.throwIfIdIsNotValid(productId, ErrorMessages.INVALID_PRODUCT_ID);
        return WarehouseProductResponse.fromEntity
                (getWarehouseProductByProductIdOrThrowIfNotExist(productId, ErrorMessages.WAREHOUSE_NOT_FOUND_BY_PRODUCT_ID));
    }


    /**
     * Adds a new product to the warehouse.
     *
     * @param warehouseProductRequest The request containing product ID and quantity.
     * @return The created warehouse product as a response DTO.
     * @throws IllegalArgumentException If the request is null or contains invalid data.
     * @throws ProductNotFoundException If the specified product does not exist.
     */
    @Transactional
    public WarehouseProductResponse saveWarehouseProduct(WarehouseProductRequest warehouseProductRequest) {
        serviceValidator.throwIfRequestIsNull(warehouseProductRequest, ErrorMessages.INVALID_WAREHOUSE_REQUEST);
        return WarehouseProductResponse.fromEntity
                (warehouseRepository.save(buildWarehouseProduct(warehouseProductRequest)));
    }

    /**
     * Retrieves all warehouse products from the database.
     *
     * @return A list of response DTOs for all warehouse products.
     */
    public List<WarehouseProductResponse> getAllProductInWarehouse() {
        return warehouseRepository.findAll()
                .stream()
                .map(WarehouseProductResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Deletes a warehouse product by its ID.
     *
     * @param warehouseId The ID of the warehouse product to delete.
     * @throws IllegalArgumentException          If the ID is null or invalid.
     * @throws WarehouseProductNotFoundException If the warehouse product does not exist.
     */
    public void deleteWarehouseProduct(Long warehouseId) {
        serviceValidator.throwIfIdIsNotValid(warehouseId, ErrorMessages.WAREHOUSE_ID_IS_INVALID);
        warehouseRepository.delete(getWarehouseProductByProductIdOrThrowIfNotExist(warehouseId, ErrorMessages.WAREHOUSE_NOT_FOUND));
    }

    /**
     * Updates an existing warehouse product with new data from the request.
     *
     * @param warehouseProductRequest The request containing updated product ID and quantity.
     * @return The updated warehouse product as a response DTO.
     * @throws IllegalArgumentException          If the request is null or contains invalid data.
     * @throws WarehouseProductNotFoundException If the warehouse product does not exist.
     */
    @Transactional
    public WarehouseProductResponse updateWarehouseProduct(WarehouseProductRequest warehouseProductRequest) {
        serviceValidator.throwIfRequestIsNull(warehouseProductRequest, ErrorMessages.INVALID_WAREHOUSE_REQUEST);
        WarehouseProduct existingWarehouseProduct = getWarehouseProductByProductIdOrThrowIfNotExist(warehouseProductRequest.getProductId(),
                ErrorMessages.WAREHOUSE_NOT_FOUND);

        Product product = productService.getProductEntity(warehouseProductRequest.getProductId());
        existingWarehouseProduct.setProduct(product);
        existingWarehouseProduct.setQuantity(warehouseProductRequest.getQuantity());

        return WarehouseProductResponse.fromEntity(warehouseRepository.save(existingWarehouseProduct));
    }

    /**
     * Updates only the quantity of a warehouse product based on the product ID.
     *
     * @param productId The ID of the product in the warehouse.
     * @param quantity  The new quantity value.
     * @return The updated warehouse product as a response DTO.
     * @throws IllegalArgumentException          If the product ID or quantity is invalid.
     * @throws WarehouseProductNotFoundException If the product is not found in the warehouse.
     */
    @Transactional
    public WarehouseProductResponse updateQuantityByProductId(Long productId, Integer quantity) {
        serviceValidator.throwIfIdIsNotValid(productId, ErrorMessages.INVALID_PRODUCT_ID);

        if (quantity == null || quantity < 0) {
            throw new IllegalArgumentException(messageService.getMessage(ErrorMessages.INVALID_QUANTITY));
        }
        WarehouseProduct existingWarehouseProduct = getWarehouseProductByProductIdOrThrowIfNotExist
                (productId, ErrorMessages.WAREHOUSE_NOT_FOUND_BY_PRODUCT_ID);

        existingWarehouseProduct.setQuantity(quantity);
        return WarehouseProductResponse.fromEntity(warehouseRepository.save(existingWarehouseProduct));
    }

    /**
     * Builds a WarehouseProduct entity from the given request.
     *
     * @param warehouseProductRequest The request containing product ID and quantity.
     * @return A WarehouseProduct entity instance.
     * @throws IllegalArgumentException If the request is null or contains invalid data.
     * @throws ProductNotFoundException If the specified product does not exist.
     */
    private WarehouseProduct buildWarehouseProduct(WarehouseProductRequest warehouseProductRequest) {
        serviceValidator.throwIfRequestIsNull(warehouseProductRequest, ErrorMessages.INVALID_WAREHOUSE_REQUEST);

        Product product = productService.getProductEntity(warehouseProductRequest.getProductId());
        return WarehouseProduct.builder()
                .warehouseProductId(null)
                .product(product)
                .quantity(warehouseProductRequest.getQuantity())
                .build();
    }

//    /**
//     * Validates the provided WarehouseProductRequest object.
//     *
//     * @param warehouseProductRequest The request to validate.
//     * @throws IllegalArgumentException If the request is null or contains invalid fields.
//     */
//    private void throwIfRequestIsInvalid(WarehouseProductRequest warehouseProductRequest) {
//        if (warehouseProductRequest == null) {
//            throw new IllegalArgumentException(messageService.getMessage(ErrorMessages.INVALID_WAREHOUSE_REQUEST));
//        }
//        if (warehouseProductRequest.getProductId() == null || warehouseProductRequest.getProductId() <= 0
//                || warehouseProductRequest.getQuantity() == null || warehouseProductRequest.getQuantity() < 0) {
//            throw new IllegalArgumentException(messageService.getMessage(ErrorMessages.INVALID_FIELDS_IN_REQUEST));
//        }
//    }

    /**
     * Retrieves a WarehouseProduct entity by ID or throws an exception.
     *
     * @param id      The warehouse product ID.
     * @param message The message key to use in the exception.
     * @return The corresponding WarehouseProduct entity.
     * @throws WarehouseProductNotFoundException If the product is not found.
     */
    private WarehouseProduct getWarehouseProductByProductIdOrThrowIfNotExist(Long id, String message) {
        return warehouseRepository.findByProduct_ProductId(id)
                .orElseThrow(() -> new WarehouseProductNotFoundException(messageService.getMessage(message, id)));
    }
}
