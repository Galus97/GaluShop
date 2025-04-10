package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.galushop.GaluShop.dto.request.ProductImageRequest;
import pl.galushop.GaluShop.dto.request.ProductRequest;
import pl.galushop.GaluShop.dto.response.ProductImagesResponse;
import pl.galushop.GaluShop.dto.response.ProductResponse;
import pl.galushop.GaluShop.entity.Product;

import java.util.List;

/**
 * Facade service responsible for managing product operations along with their associated images.
 * This class coordinates actions between ProductService and ProductImagesService to handle
 * compound operations involving both product and image entities.
 */
@Service
@RequiredArgsConstructor
public class ProductFacadeService {
    private final ProductService productService;
    private final ProductImagesService productImagesService;

    /**
     * Saves a new product along with its associated product images.
     *
     * @param productRequest The request object containing product details and a list of images.
     * @return A response DTO representing the saved product.
     * @throws IllegalArgumentException                                if the request is null or contains invalid data.
     * @throws pl.galushop.GaluShop.exception.ProductNotFoundException if the product or any related entity is not found.
     */
    @Transactional
    public ProductResponse saveProductWithImages(ProductRequest productRequest) {
        Product product = productService.saveProductEntity(productRequest);

        for (ProductImageRequest imageRequest : productRequest.getProductImages()) {
            imageRequest.setProduct(product);
            productImagesService.saveProductImages(imageRequest);
        }
        return ProductResponse.fromEntity(product);
    }

    /**
     * Retrieves all images associated with the specified product ID.
     *
     * @param productId The ID of the product.
     * @return A list of response DTOs representing the product images.
     * @throws IllegalArgumentException                                if the product ID is null or invalid.
     * @throws pl.galushop.GaluShop.exception.ProductNotFoundException if the product does not exist.
     */
    public List<ProductImagesResponse> getAllImagesByProductId(Long productId) {
        productService.getProductEntity(productId);
        return productImagesService.getAllImagesByProductId(productId);
    }
}
