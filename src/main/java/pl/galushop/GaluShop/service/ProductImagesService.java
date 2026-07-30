package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.galushop.GaluShop.util.ServiceValidator;
import pl.galushop.GaluShop.component.ErrorMessages;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.dto.request.ProductImageRequest;
import pl.galushop.GaluShop.dto.response.ProductImagesResponse;
import pl.galushop.GaluShop.model.ProductImages;
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
    private final ServiceValidator serviceValidator;

    /**
     * Retrieves a product image by its ID.
     *
     * @param imagesId The ID of the product image to retrieve.
     * @return A response DTO representing the product image.
     * @throws IllegalArgumentException       If the image ID is null or invalid.
     * @throws ProductImagesNotFoundException If no image is found with the given ID.
     */
    public ProductImagesResponse getProductImages(Long imagesId) {
        serviceValidator.throwIfIdIsNotValid(imagesId, ErrorMessages.INVALID_PRODUCT_IMAGES_ID);
        return ProductImagesResponse.fromEntity(getImagesOrThrow(imagesId));
    }

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
     * Updates an existing product image's details.
     *
     * @param productImageRequest The request object containing updated product image details.
     * @return A response DTO representing the updated product image.
     * @throws IllegalArgumentException       If the request object is null or contains an invalid image ID.
     * @throws ProductImagesNotFoundException If no image is found with the given ID.
     */
    @Transactional
    public ProductImagesResponse updateProductImages(ProductImageRequest productImageRequest) {
        serviceValidator.throwIfRequestIsNull(productImageRequest, ErrorMessages.PRODUCT_IMAGES_REQUEST_IS_NULL);
        serviceValidator.throwIfIdIsNotValid(productImageRequest.getImagesId(), ErrorMessages.INVALID_PRODUCT_IMAGES_ID);

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
    @Transactional
    public void deleteProductImages(Long imagesId) {
        serviceValidator.throwIfIdIsNotValid(imagesId, ErrorMessages.INVALID_PRODUCT_IMAGES_ID);
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
        serviceValidator.throwIfIdIsNotValid(productId, ErrorMessages.INVALID_PRODUCT_ID);
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
        serviceValidator.throwIfRequestIsNull(productImageRequest, ErrorMessages.PRODUCT_IMAGES_REQUEST_IS_NULL);
        return ProductImages.builder()
                .product(productImageRequest.getProduct())
                .imgSrc(productImageRequest.getImgSrc())
                .altImg(productImageRequest.getAltImg())
                .build();
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
