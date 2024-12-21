package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void saveProductToDatabase(Product product){
        if (product != null) {
            productRepository.save(product);
        }
    }

    public void deleteProduct(Long id){
        if(productRepository.findByProductId(id) != null){
            productRepository.delete(productRepository.findByProductId(id));
        }
    }
}
