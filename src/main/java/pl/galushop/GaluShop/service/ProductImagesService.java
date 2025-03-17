package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.galushop.GaluShop.component.ErrorMessages;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.dto.request.ProductImageRequest;
import pl.galushop.GaluShop.entity.ProductImages;
import pl.galushop.GaluShop.exception.ProductImagesNotFoundException;
import pl.galushop.GaluShop.repository.ProductImagesRepository;

import java.util.List;

/**
 * Service class responsible for managing product images operations.
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
     * @return The created ProductImages
     * @throws IllegalArgumentException if the request object is null or contains an invalid image ID.
     */
    @Transactional
    public ProductImages saveProductImages(ProductImageRequest productImageRequest) {
        return productImagesRepository.save(buildProductImages(productImageRequest));
    }

    /**
     * Retrieves a product image by its ID.
     *
     * @param imagesId The ID of the product image to retrieve.
     * @return The retrieved product image entity.
     * @throws IllegalArgumentException if the image ID is null or invalid.
     * @throws ProductImagesNotFoundException if no image is found with the given ID.
     */
    public ProductImages getProductImages(Long imagesId) {
        throwIfIdIsInvalid(imagesId);
        return getImagesOrThrow(imagesId);
    }

    /**
     * Updates an existing product image's details.
     *
     * @param productImageRequest The request object containing updated product image details.
     * @throws IllegalArgumentException if the request object contains an invalid image ID.
     * @throws ProductImagesNotFoundException if no image is found with the given ID.
     */
    @Transactional
    public void updateProductImages(ProductImageRequest productImageRequest) {
        throwIfIdIsInvalid(productImageRequest.getImagesId());

        ProductImages exisitngProductImages = getImagesOrThrow(productImageRequest.getImagesId());
        exisitngProductImages.setProduct(productImageRequest.getProduct());
        exisitngProductImages.setImgSrc(productImageRequest.getImgSrc());
        exisitngProductImages.setAltImg(productImageRequest.getAltImg());

        productImagesRepository.save(exisitngProductImages);
    }

    /**
     * Deletes a product image by its ID.
     *
     * @param imagesId The ID of the product image to delete.
     * @throws IllegalArgumentException if the image ID is null or invalid.
     * @throws ProductImagesNotFoundException if no image is found with the given ID.
     */
    public void deleteProductImages(Long imagesId) {
        throwIfIdIsInvalid(imagesId);
        productImagesRepository.delete(getImagesOrThrow(imagesId));
    }

    /**
     * Retrieves all images associated with a specific product ID.
     *
     * @param productId The ID of the product.
     * @return A list of product images associated with the product.
     * @throws IllegalArgumentException if the product ID is null or invalid.
     */
    public List<ProductImages> getAllImagesByProductId(Long productId) {
        throwIfIdIsInvalid(productId);
        return productImagesRepository.findAllByProduct_ProductId(productId);
    }

    /**
     * Builds a ProductImages entity from the given request
     *
     * @param productImageRequest The request object containing product image details.
     * @return A new ProductImages instance
     * @throws IllegalArgumentException if the request object is null or contains an invalid image ID.
     */
    private ProductImages buildProductImages(ProductImageRequest productImageRequest) {
        throwIfIdIsInvalid(productImageRequest.getImagesId());

        return ProductImages.builder()
                .product(productImageRequest.getProduct())
                .imgSrc(productImageRequest.getImgSrc())
                .altImg(productImageRequest.getAltImg())
                .build();
    }

    private void throwIfIdIsInvalid(Long id){
        if(id == null || id <= 0){
            throw new IllegalArgumentException(messageService.getMessage(ErrorMessages.INVALID_PRODUCT_IMAGES_ID, id));
        }
    }

    private ProductImages getImagesOrThrow(Long imagesId) {
        return productImagesRepository.findById(imagesId)
                .orElseThrow(() -> new ProductImagesNotFoundException(messageService.getMessage(ErrorMessages.PRODUCT_IMAGES_NOT_FOUND, imagesId)));
    }
}
