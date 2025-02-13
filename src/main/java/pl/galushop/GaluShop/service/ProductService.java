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
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImagesService productImagesService;
    private final MessageService messageService;

    public void saveProductToDatabase(ProductRequest productRequest) {
        if(productRequest == null){
            throw new IllegalArgumentException();
        }
        Product product = new Product();
        product.setProductName(productRequest.getProductName());
        product.setCategory(productRequest.getCategory());
        product.setPrice(productRequest.getPrice());
        product.setCategoryId(productRequest.getCategoryId());
        product.setDescription(productRequest.getDescription());
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

    public void deleteProduct(Long productId) {
        if(productId == null || productId < 0){
            throw new IllegalArgumentException(messageService.getMessage("error.invalidProductId", productId));
        }
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductNotFoundException(messageService.getMessage("error.productNotFound", productId)));
        productRepository.delete(product);
    }

    public Product findProductById(Long id) {
        if (id != null && id > 0) {
            if(productRepository.findByProductId(id).isPresent()){
                return productRepository.findByProductId(id).get();
            }
            throw new NoSuchElementException("That product doesn't exist in database");
        } else {
            throw new IllegalArgumentException("Product Id is invalid");
        }
    }

    public void updateProductByProductId(Long productId, String productName, String description,
                                         Double price, String category, Integer categoryId){
        if(productId > 0 && productRepository.findByProductId(productId).isPresent()){
            if(checkFields(productName, description, price, category, categoryId)){
                productRepository.updateProductByProductId(productId, productName, description, price, category, categoryId);
            }
           throw new IllegalArgumentException("Some fields are invalid");
        }
        throw new IllegalArgumentException("Product Id is invalid");
    }

    private boolean checkFields(String productName, String description, Double price, String category, Integer categoryId){
        if(productName.isBlank() || description.isBlank() || price < 0 || category.isBlank() || categoryId < 0){
            return false;
        }
        return true;
    }
}
