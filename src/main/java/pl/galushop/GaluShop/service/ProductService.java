package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.galushop.GaluShop.util.ServiceValidator;
import pl.galushop.GaluShop.component.ErrorMessages;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.dto.request.ProductRequest;
import pl.galushop.GaluShop.dto.response.ProductResponse;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.exception.ProductNotFoundException;
import pl.galushop.GaluShop.repository.ProductRepository;

import java.util.List;

/**
 * Service class responsible for managing product operations.
 * It handles creating, reading, updating, and deleting (CRUD) product data
 */
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final MessageService messageService;
    private final ServiceValidator serviceValidator;

    /**
     * Retrieves a product entity by its ID.
     *
     * @param productId The ID of the product to retrieve.
     * @return The retrieved product entity.
     * @throws IllegalArgumentException If the product ID is null or invalid.
     * @throws ProductNotFoundException If no product is found with the given ID.
     */
    public Product getProductEntity(Long productId) {
        serviceValidator.throwIfIdIsNotValid(productId, ErrorMessages.INVALID_PRODUCT_ID);
        return getProductOrThrow(productId);
    }

    /**
     * Retrieves a product as a response DTO by its ID.
     *
     * @param productId The ID of the product to retrieve.
     * @return A ProductResponse representing the product.
     * @throws IllegalArgumentException If the product ID is null or invalid.
     * @throws ProductNotFoundException If the product is not found.
     */
    public ProductResponse getProductResponse(Long productId) {
        serviceValidator.throwIfIdIsNotValid(productId, ErrorMessages.INVALID_PRODUCT_ID);
        return ProductResponse.fromEntity(getProductOrThrow(productId));
    }

    /**
     * Saves a new product to the database.
     *
     * @param productRequest The request containing product details.
     * @return The created product entity.
     * @throws IllegalArgumentException If the product request is null.
     */
    @Transactional
    public Product saveProductEntity(ProductRequest productRequest) {
        serviceValidator.throwIfRequestIsNull(productRequest, ErrorMessages.INVALID_PRODUCT_REQUEST);
        return productRepository.save(buildProduct(productRequest));
    }

    /**
     * Saves a new product and returns it as a response DTO.
     *
     * @param productRequest The request containing product details.
     * @return The created product as a ProductResponse.
     * @throws IllegalArgumentException If the product request is null.
     */
    @Transactional
    public ProductResponse saveProductResponse(ProductRequest productRequest) {
        serviceValidator.throwIfRequestIsNull(productRequest, ErrorMessages.INVALID_PRODUCT_REQUEST);
        return ProductResponse.fromEntity(productRepository.save(buildProduct(productRequest)));
    }

    /**
     * Deletes a product by its ID.
     *
     * @param productId The ID of the product to delete.
     * @throws IllegalArgumentException If the product ID is null or invalid.
     * @throws ProductNotFoundException If no product is found with the given ID.
     */
    public void deleteProduct(Long productId) {
        serviceValidator.throwIfIdIsNotValid(productId, ErrorMessages.INVALID_PRODUCT_ID);
        productRepository.delete(getProductOrThrow(productId));
    }

    /**
     * Updates an existing product with new data.
     *
     * @param productRequest The request containing updated product data.
     * @return The updated product as a ProductResponse.
     * @throws IllegalArgumentException If the product request or ID is invalid.
     * @throws ProductNotFoundException If the product is not found.
     */
    @Transactional
    public ProductResponse updateProduct(ProductRequest productRequest) {
        serviceValidator.throwIfRequestIsNull(productRequest, ErrorMessages.INVALID_PRODUCT_REQUEST);
        serviceValidator.throwIfIdIsNotValid(productRequest.getProductId(), ErrorMessages.INVALID_PRODUCT_ID);

        Product existingProduct = getProductOrThrow(productRequest.getProductId());
        existingProduct.setProductName(productRequest.getProductName());
        existingProduct.setDescription(productRequest.getDescription());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setCategory(productRequest.getCategory());
        existingProduct.setCategoryId(productRequest.getCategoryId());

        return ProductResponse.fromEntity(productRepository.save(existingProduct));
    }

    /**
     * Retrieves a list of products by their IDs.
     *
     * @param productIds The list of product IDs.
     * @return A list of product entities.
     * @throws IllegalArgumentException If the product ID list is null or empty.
     */
    public List<Product> getAllProductByIds(List<Long> productIds) {
        if (productIds == null || productIds.isEmpty()) {
            throw new IllegalArgumentException(messageService.getMessage(ErrorMessages.LIST_IS_INVALID));
        }
        return productRepository.findAllById(productIds);
    }

    /**
     * Builds a Product entity from the given request.
     *
     * @param productRequest The request containing product details.
     * @return A Product entity.
     * @throws IllegalArgumentException If the product request is null.
     */
    private Product buildProduct(ProductRequest productRequest) {
        serviceValidator.throwIfRequestIsNull(productRequest, ErrorMessages.INVALID_PRODUCT_REQUEST);
        return Product.builder()
                .productName(productRequest.getProductName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .category(productRequest.getCategory())
                .categoryId(productRequest.getCategoryId())
                .build();
    }
//
//    /**
//     * Validates whether the request is null.
//     *
//     * @param productRequest The product request.
//     * @throws IllegalArgumentException If the request is null.
//     */
//    private void throwIfRequestIsNull(ProductRequest productRequest) {
//        if (productRequest == null) {
//            throw new IllegalArgumentException(messageService.getMessage(ErrorMessages.INVALID_PRODUCT_REQUEST));
//        }
//    }
//
//    /**
//     * Validates whether the provided ID is non-null and positive.
//     *
//     * @param id The product ID to validate.
//     * @throws IllegalArgumentException If the ID is null or invalid.
//     */
//    private void throwIfIdIsInvalid(Long id) {
//        if (id == null || id <= 0) {
//            throw new IllegalArgumentException(messageService.getMessage(ErrorMessages.INVALID_PRODUCT_ID, id));
//        }
//    }

    /**
     * Retrieves a product entity by ID or throws an exception.
     *
     * @param productId The ID of the product.
     * @return The found Product entity.
     * @throws ProductNotFoundException If no product is found with the given ID.
     */
    private Product getProductOrThrow(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(messageService.getMessage(ErrorMessages.PRODUCT_NOT_FOUND, productId)));
    }
}
