package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.entity.ProductImages;
import pl.galushop.GaluShop.repository.ProductImagesRepository;
import pl.galushop.GaluShop.repository.ProductRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProductImagesService {

    private final ProductImagesRepository productImagesRepository;

    public void saveProductImagesToDatabase(ProductImages productImages) {
        productImagesRepository.save(productImages);
    }

    public void deleteProductImages(Long imagesId){
        if(imagesId != null && imagesId > 0){
            if(productImagesRepository.existsById(imagesId)){
                productImagesRepository.deleteById(imagesId);
            } else {
                throw new NoSuchElementException("That Image doesn't exist in database");
            }
        } else {
            throw new IllegalArgumentException("Image Product Id is invalid");
        }
    }

    public List<ProductImages> getAllImagesByProductId(Long productId){
        if(productId != null && productId > 0){
            if(productImagesRepository.existsByProduct_ProductId(productId)){
                return productImagesRepository.findAllByProduct_ProductId(productId);
            }
            throw new NoSuchElementException("Images doesn't exist in database");
        }
        throw new IllegalArgumentException("Product Id is invalid");
    }
}
