package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.galushop.GaluShop.dto.ProductImageRequest;
import pl.galushop.GaluShop.dto.ProductRequest;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.entity.ProductImages;

import java.util.List;

/**
 * Facade service that manages interactions between ProductService and ProductImagesService.
 */
@Service
@RequiredArgsConstructor
public class ProductFacadeService {
    private final ProductService productService;
    private final ProductImagesService productImagesService;

    /**
     * Saves a new product along with its associated images.
     *
     * @param productRequest The request object containing product details and images.
     */
    @Transactional
    public Product saveProductWithImages(ProductRequest productRequest){
        Product product = productService.saveProduct(productRequest);

        for(ProductImageRequest imageRequest : productRequest.getProductImages()){
            imageRequest.setProduct(product);
            productImagesService.saveProductImages(imageRequest);
        }
        return product;
    }

    /**
     * Retrieves all images for a given product ID.
     *
     * @param productId The ID of the product.
     * @return List of product images.
     */
    public List<ProductImages> getAllImagesByProductId(Long productId) {
        productService.getProduct(productId);
        return productImagesService.getAllImagesByProductId(productId);
    }
}
