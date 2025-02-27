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
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProductImagesService {
    private final ProductImagesRepository productImagesRepository;
    private final MessageService messageService;

    public void saveProductImagesToDatabase(ProductImages productImages) {
        if (productImages == null) {
            throw new IllegalArgumentException(messageService.getMessage("error.productImagesIsNull"));
        }
        if (productImages.getImagesId() == null || productImages.getImagesId() < 0) {
            throw new IllegalArgumentException(messageService.getMessage("error.invalidProductImagesId", productImages.getImagesId()));
        }
        productImagesRepository.save(productImages);
    }

    public ProductImages getProductImages(Long imagesId){
        if(imagesId == null || imagesId < 0){
            throw new IllegalArgumentException(messageService.getMessage("error.invalidProductImagesId", imagesId));
        }
        return productImagesRepository.findById(imagesId)
                .orElseThrow(() -> new ProductImagesNotFoundException(messageService.getMessage("error.productImagesNotFoundException", imagesId)));
    }

    @Transactional
    public void updateProductImages(ProductImageRequest productImageRequest){
        if(productImageRequest.getImagesId() == null || productImageRequest.getImagesId() < 0){
            throw new IllegalArgumentException(messageService.getMessage("error.invalidProductImagesId", productImageRequest.getImagesId()));
        }
        ProductImages exisitngProductImages = productImagesRepository.findById(productImageRequest.getImagesId())
                .orElseThrow(() -> new ProductImagesNotFoundException(messageService.getMessage("error.productImagesNotFoundException", productImageRequest.getImagesId())));

        exisitngProductImages.setProduct(productImageRequest.getProduct());
        exisitngProductImages.setImgSrc(productImageRequest.getImgSrc());
        exisitngProductImages.setAltImg(productImageRequest.getAltImg());

        productImagesRepository.save(exisitngProductImages);
    }


    public void deleteProductImages(Long imagesId) {
        if (imagesId != null && imagesId > 0) {
            if (productImagesRepository.existsById(imagesId)) {
                productImagesRepository.deleteById(imagesId);
            } else {
                throw new NoSuchElementException("That Image doesn't exist in database");
            }
        } else {
            throw new IllegalArgumentException("Image Product Id is invalid");
        }
    }

    public List<ProductImages> getAllImagesByProductId(Long productId) {
        if (productId != null && productId > 0) {
            if (productImagesRepository.existsByProduct_ProductId(productId)) {
                return productImagesRepository.findAllByProduct_ProductId(productId);
            }
            throw new NoSuchElementException("Images doesn't exist in database");
        }
        throw new IllegalArgumentException("Product Id is invalid");
    }
}
