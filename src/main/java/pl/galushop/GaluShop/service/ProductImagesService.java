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
    private final ProductService productService;
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
        if(imagesId == null || imagesId < 0){
            throw new IllegalArgumentException(messageService.getMessage("error.invalidProductImagesId", imagesId));
        }
        ProductImages productImages = productImagesRepository.findById(imagesId)
                .orElseThrow(() -> new ProductImagesNotFoundException(messageService.getMessage("error.productImagesNotFoundException", imagesId)));
        productImagesRepository.delete(productImages);
    }

    public List<ProductImages> getAllImagesByProductId(Long productId) {
        if(productId == null || productId < 0){
            throw new IllegalArgumentException(messageService.getMessage("error.invalidProductId", productId));
        }
        //throw ProductNotFoundException if product doesn't exist id database
        productService.getProduct(productId);

        return productImagesRepository.findAllByProduct_ProductId(productId);
    }
}
