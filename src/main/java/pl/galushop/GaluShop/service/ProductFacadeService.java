package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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


    public void saveProductWithImages(ProductRequest productRequest){
        Product product = productService.saveProduct(productRequest);

        for(ProductImageRequest imageRequest : productRequest.getProductImages()){
            imageRequest.setProduct(product);
            productImagesService.saveProductImages(imageRequest);
        }
    }


    public List<ProductImages> getAllImagesByProductId(Long productId) {
        productService.getProduct(productId);
        return productImagesService.getAllImagesByProductId(productId);
    }
}
