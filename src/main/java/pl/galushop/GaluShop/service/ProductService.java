package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.component.MessageService;
import pl.galushop.GaluShop.dto.ProductImageRequest;
import pl.galushop.GaluShop.dto.ProductRequest;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.entity.ProductImages;
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
    private final ProductImagesService productImagesService;
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
        if (productId == null || productId < 0) {
            throw new IllegalArgumentException(messageService.getMessage("error.invalidProductId", productId));
        }
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(messageService.getMessage("error.productNotFound", productId)));
    }

    /**
     * Saves a new product along with its associated images.
     *
     * @param productRequest The request object containing product details and images.
     * @throws IllegalArgumentException if the product request is null.
     */
    public void saveProduct(ProductRequest productRequest) {
        if(productRequest == null){
            throw new IllegalArgumentException();
        }
        Product product = new Product();
        setProductFields(productRequest, product);
        productRepository.save(product);

        List<ProductImageRequest> productImagesList = productRequest.getProductImages();
        for (ProductImageRequest productImage : productImagesList) {
            ProductImages productImages = new ProductImages();
            productImages.setProduct(product);
            productImages.setImgSrc(productImage.getImgSrc());
            productImages.setAltImg(productImage.getAltImg());
            productImagesService.saveProductImagesToDatabase(productImages);
        }
    }

    /**
     * Deletes a product by its ID.
     *
     * @param productId The ID of the product to delete.
     * @throws IllegalArgumentException if the product ID is null or invalid.
     * @throws ProductNotFoundException if no product is found with the given ID.
     */
    public void deleteProduct(Long productId) {
        if(productId == null || productId < 0){
            throw new IllegalArgumentException(messageService.getMessage("error.invalidProductId", productId));
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(messageService.getMessage("error.productNotFound", productId)));
        productRepository.delete(product);
    }

    /**
     * Updates an existing product's details.
     *
     * @param productRequest The request object containing updated product details.
     * @throws IllegalArgumentException if the product request is null or contains an invalid ID.
     * @throws ProductNotFoundException if no product is found with the given ID.
     */
    public void updateProduct(ProductRequest productRequest) {
        if(productRequest == null || productRequest.getProductId() < 0){
            throw new IllegalArgumentException(messageService.getMessage("error.invalidProductId", productRequest.getProductId()));
        }
        Product existingProduct = productRepository.findById(productRequest.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(messageService.getMessage("error.productNotFound", productRequest.getProductId())));
        setProductFields(productRequest, existingProduct);

        productRepository.save(existingProduct);
    }

    /**
     * Sets the common fields for a product entity.
     *
     * @param productRequest The request object containing product details.
     * @param product The product entity to update.
     */
    private static void setProductFields(ProductRequest productRequest, Product product){
        product.setProductName(productRequest.getProductName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setCategory(productRequest.getCategory());
        product.setCategoryId(productRequest.getCategoryId());
    }
}
