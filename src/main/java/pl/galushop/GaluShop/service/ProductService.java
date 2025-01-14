package pl.galushop.GaluShop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.galushop.GaluShop.entity.Product;
import pl.galushop.GaluShop.repository.ProductRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void saveProductToDatabase(Product product) {
        if (product != null) {
            productRepository.save(product);
        }
    }

    public void deleteProduct(Long id) {
        if(id != null && id > 0) {
            if (productRepository.findByProductId(id).isPresent()) {
                productRepository.delete(productRepository.findByProductId(id).get());
            }
        }
    }

    public Product findProductById(Long id) {
        if (id != null && id > 0) {
            if(productRepository.findByProductId(id).isPresent()){
                return productRepository.findByProductId(id).get();
            }
            throw new NoSuchElementException("That product doesn't exist in database");
        } else {
            throw new IllegalArgumentException("Wrong Product Id");
        }
    }

}
