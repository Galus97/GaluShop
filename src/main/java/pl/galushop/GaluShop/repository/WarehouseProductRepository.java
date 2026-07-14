package pl.galushop.GaluShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.galushop.GaluShop.model.WarehouseProduct;

import java.util.Optional;

public interface WarehouseProductRepository extends JpaRepository<WarehouseProduct, Long> {

    Optional<WarehouseProduct> findByProduct_ProductId(Long productId);
}
