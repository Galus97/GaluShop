package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.dto.ProductImageRequest;
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
     * @throws IllegalArgumentException if the request object is null or contains an invalid image ID.
     */
    public void saveProductImages(ProductImageRequest productImageRequest) {
        if (productImageRequest.getImagesId() == null || productImageRequest.getImagesId() < 0) {
            throw new IllegalArgumentException(messageService.getMessage("error.invalidProductImagesId", productImageRequest.getImagesId()));
        }
        ProductImages productImages = new ProductImages();
        productImages.setProduct(productImageRequest.getProduct());
        productImages.setImgSrc(productImageRequest.getImgSrc());
        productImages.setAltImg(productImageRequest.getAltImg());

        productImagesRepository.save(productImages);
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
        if (imagesId == null || imagesId < 0) {
            throw new IllegalArgumentException(messageService.getMessage("error.invalidProductImagesId", imagesId));
        }
        return productImagesRepository.findById(imagesId)
                .orElseThrow(() -> new ProductImagesNotFoundException(messageService.getMessage("error.productImagesNotFoundException", imagesId)));
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
        if (productImageRequest.getImagesId() == null || productImageRequest.getImagesId() < 0) {
            throw new IllegalArgumentException(messageService.getMessage("error.invalidProductImagesId", productImageRequest.getImagesId()));
        }
        ProductImages exisitngProductImages = productImagesRepository.findById(productImageRequest.getImagesId())
                .orElseThrow(() -> new ProductImagesNotFoundException(messageService.getMessage("error.productImagesNotFoundException", productImageRequest.getImagesId())));

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
        if (imagesId == null || imagesId < 0) {
            throw new IllegalArgumentException(messageService.getMessage("error.invalidProductImagesId", imagesId));
        }
        ProductImages productImages = productImagesRepository.findById(imagesId)
                .orElseThrow(() -> new ProductImagesNotFoundException(messageService.getMessage("error.productImagesNotFoundException", imagesId)));
        productImagesRepository.delete(productImages);
    }

    /**
     * Retrieves all images associated with a specific product ID.
     *
     * @param productId The ID of the product.
     * @return A list of product images associated with the product.
     * @throws IllegalArgumentException if the product ID is null or invalid.
     */
    public List<ProductImages> getAllImagesByProductId(Long productId) {
        if (productId == null || productId < 0) {
            throw new IllegalArgumentException(messageService.getMessage("error.invalidProductId", productId));
        }

        return productImagesRepository.findAllByProduct_ProductId(productId);
    }
}
