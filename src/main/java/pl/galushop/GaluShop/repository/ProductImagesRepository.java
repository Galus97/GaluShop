package pl.galushop.GaluShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.galushop.GaluShop.entity.ProductImages;

public interface ProductImagesRepository extends JpaRepository<ProductImages, Long> {
}
