package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.galushop.GaluShop.component.ErrorMessages;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.dto.request.ProductImageRequest;
import pl.galushop.GaluShop.dto.response.ProductImagesResponse;
import pl.galushop.GaluShop.entity.ProductImages;
import pl.galushop.GaluShop.exception.ProductImagesNotFoundException;
import pl.galushop.GaluShop.repository.ProductImagesRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class responsible for managing product image operations.
 * It handles creating, reading, updating, and deleting (CRUD) product image data
 * and retrieving images associated with specific products.
 */
@Service
@RequiredArgsConstructor
public class ProductImagesService {
    private final ProductImagesRepository productImagesRepository;
    private final MessageService messageService;

    /**
     * Saves a new product image to the database.
     *
     * @param productImageRequest The request object containing product image details.
     * @return The created product image as a response DTO.
     * @throws IllegalArgumentException If the request object is null or contains invalid data.
     */
    @Transactional
    public ProductImagesResponse saveProductImages(ProductImageRequest productImageRequest) {
        return ProductImagesResponse.fromEntity(productImagesRepository.save(buildProductImages(productImageRequest)));
    }

    /**
     * Retrieves a product image by its ID.
     *
     * @param imagesId The ID of the product image to retrieve.
     * @return A response DTO representing the product image.
     * @throws IllegalArgumentException       If the image ID is null or invalid.
     * @throws ProductImagesNotFoundException If no image is found with the given ID.
     */
    public ProductImagesResponse getProductImages(Long imagesId) {
        throwIfIdIsInvalid(imagesId);
        return ProductImagesResponse.fromEntity(getImagesOrThrow(imagesId));
    }

    /**
     * Updates an existing product image's details.
     *
     * @param productImageRequest The request object containing updated product image details.
     * @return A response DTO representing the updated product image.
     * @throws IllegalArgumentException       If the request object is null or contains an invalid image ID.
     * @throws ProductImagesNotFoundException If no image is found with the given ID.
     */
    @Transactional
    public ProductImagesResponse updateProductImages(ProductImageRequest productImageRequest) {
        throwIfRequestIsNull(productImageRequest);
        throwIfIdIsInvalid(productImageRequest.getImagesId());

        ProductImages exisitngProductImages = getImagesOrThrow(productImageRequest.getImagesId());
        exisitngProductImages.setProduct(productImageRequest.getProduct());
        exisitngProductImages.setImgSrc(productImageRequest.getImgSrc());
        exisitngProductImages.setAltImg(productImageRequest.getAltImg());

        return ProductImagesResponse.fromEntity(productImagesRepository.save(exisitngProductImages));
    }

    /**
     * Deletes a product image by its ID.
     *
     * @param imagesId The ID of the product image to delete.
     * @throws IllegalArgumentException       If the image ID is null or invalid.
     * @throws ProductImagesNotFoundException If no image is found with the given ID.
     */
    public void deleteProductImages(Long imagesId) {
        throwIfIdIsInvalid(imagesId);
        productImagesRepository.delete(getImagesOrThrow(imagesId));
    }

    /**
     * Retrieves all images associated with a specific product ID.
     *
     * @param productId The ID of the product.
     * @return A list of response DTOs representing product images.
     * @throws IllegalArgumentException If the product ID is null or invalid.
     */
    public List<ProductImagesResponse> getAllImagesByProductId(Long productId) {
        throwIfIdIsInvalid(productId);
        return productImagesRepository.findAllByProduct_ProductId(productId)
                .stream()
                .map(ProductImagesResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Builds a ProductImages entity from the given request.
     *
     * @param productImageRequest The request object containing product image details.
     * @return A ProductImages entity instance.
     * @throws IllegalArgumentException If the request object is null or contains invalid data.
     */
    private ProductImages buildProductImages(ProductImageRequest productImageRequest) {
        throwIfRequestIsNull(productImageRequest);
        return ProductImages.builder()
                .product(productImageRequest.getProduct())
                .imgSrc(productImageRequest.getImgSrc())
                .altImg(productImageRequest.getAltImg())
                .build();
    }

    /**
     * Validates the provided ProductImageRequest object.
     *
     * @param productImageRequest The request to validate.
     * @throws IllegalArgumentException If the request is null.
     */
    private void throwIfRequestIsNull(ProductImageRequest productImageRequest) {
        if (productImageRequest == null) {
            throw new IllegalArgumentException(messageService.getMessage(ErrorMessages.PRODUCT_IMAGES_REQUEST_IS_NULL));
        }
    }

    /**
     * Validates whether the provided ID is non-null and positive.
     *
     * @param id The ID to validate.
     * @throws IllegalArgumentException If the ID is null or invalid.
     */
    private void throwIfIdIsInvalid(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(messageService.getMessage(ErrorMessages.INVALID_PRODUCT_IMAGES_ID, id));
        }
    }

    /**
     * Retrieves a ProductImages entity by ID or throws an exception.
     *
     * @param imagesId The product image ID.
     * @return The corresponding ProductImages entity.
     * @throws ProductImagesNotFoundException If the product image is not found.
     */
    private ProductImages getImagesOrThrow(Long imagesId) {
        return productImagesRepository.findById(imagesId)
                .orElseThrow(() -> new ProductImagesNotFoundException(messageService.getMessage(ErrorMessages.PRODUCT_IMAGES_NOT_FOUND, imagesId)));
    }
}
