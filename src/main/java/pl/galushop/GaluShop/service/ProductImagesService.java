package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.entity.ProductImages;
import pl.galushop.GaluShop.repository.ProductImagesRepository;

@Service
@RequiredArgsConstructor
public class ProductImagesService {

    private final ProductImagesRepository productImagesRepository;

    public void saveProductImagesToDatabase(ProductImages productImages) {
        productImagesRepository.save(productImages);
    }
}
