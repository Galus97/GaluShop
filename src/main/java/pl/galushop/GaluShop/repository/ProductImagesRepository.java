package pl.galushop.GaluShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.galushop.GaluShop.entity.ProductImages;

import java.util.List;

public interface ProductImagesRepository extends JpaRepository<ProductImages, Long> {

    List<ProductImages> findAllByProduct_ProductId(Long productId);

    boolean existsByProduct_ProductId(Long productId);
}
