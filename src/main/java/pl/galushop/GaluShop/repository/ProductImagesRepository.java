package pl.galushop.GaluShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.galushop.GaluShop.model.Product;
import pl.galushop.GaluShop.model.ProductImages;

import java.util.List;

public interface ProductImagesRepository extends JpaRepository<ProductImages, Long> {

    List<ProductImages> findAllByProduct_ProductId(Long productId);

    List<ProductImages> findAllByProduct(Product product);
}
