package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.galushop.GaluShop.component.ErrorMessages;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.dto.ProductRequest;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.exception.ProductNotFoundException;
import pl.galushop.GaluShop.repository.ProductRepository;

import java.util.List;

/**
 * Service class responsible for managing product operations.
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final MessageService messageService;

    /**
     * Retrieves a product by its ID.
     *
     * @param productId The ID of the product to retrieve.
     * @return The retrieved product entity.
     * @throws IllegalArgumentException if the product ID is null or invalid.
     * @throws ProductNotFoundException if no product is found with the given ID.
     */
    public Product getProduct(Long productId) {
        throwIfIdIsInvalid(productId);
        return getProductOrThrow(productId);
    }

    /**
     * Saves a new product to the database.
     *
     * @param productRequest The request object containing product details and images.
     * @throws IllegalArgumentException if the product request is null.
     */
    @Transactional
    public Product saveProduct(ProductRequest productRequest) {
        return productRepository.save(buildProduct(productRequest));
    }


    /**
     * Deletes a product by its ID.
     *
     * @param productId The ID of the product to delete.
     * @throws IllegalArgumentException if the product ID is null or invalid.
     * @throws ProductNotFoundException if no product is found with the given ID.
     */
    public void deleteProduct(Long productId) {
        throwIfIdIsInvalid(productId);
        productRepository.delete(getProductOrThrow(productId));
    }

    /**
     * Updates an existing product's details.
     *
     * @param productRequest The request object containing updated product details.
     * @throws IllegalArgumentException if the product request is null or contains an invalid ID.
     * @throws ProductNotFoundException if no product is found with the given ID.
     */
    @Transactional
    public void updateProduct(ProductRequest productRequest) {
        throwIfIdIsInvalid(productRequest.getProductId());

        Product existingProduct = getProductOrThrow(productRequest.getProductId());
        existingProduct.setProductName(productRequest.getProductName());
        existingProduct.setDescription(productRequest.getDescription());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setCategory(productRequest.getCategory());
        existingProduct.setCategoryId(productRequest.getCategoryId());

        productRepository.save(existingProduct);
    }

    /**
     * Retrieves a list of products by their IDs.
     *
     * @param productIds The list of product IDs to retrieve.
     * @return A list of retrieved product entities.
     * @throws IllegalArgumentException if the provided list is null.
     */
    public List<Product> getAllProductByIds(List<Long> productIds){
        if(productIds == null || productIds.isEmpty()){
            throw new IllegalArgumentException(messageService.getMessage(ErrorMessages.LIST_IS_INVALID));
        }
        return productRepository.findAllById(productIds);
    }

    /**
     * Builds Product entity from the given request
     *
     * @param productRequest The request object containing product details and images.
     * @return A new Product instance
     * @throws IllegalArgumentException if the product request is null.
     */
    private Product buildProduct(ProductRequest productRequest) {
        throwIfIdIsInvalid(productRequest.getProductId());

        return Product.builder()
                .productName(productRequest.getProductName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .category(productRequest.getCategory())
                .categoryId(productRequest.getCategoryId())
                .build();

    }

    private void throwIfIdIsInvalid(Long id){
        if(id == null || id <= 0){
            throw new IllegalArgumentException(messageService.getMessage(ErrorMessages.INVALID_PRODUCT_ID, id));
        }
    }

    private Product getProductOrThrow(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(messageService.getMessage(ErrorMessages.PRODUCT_NOT_FOUND, productId)));
    }
}
