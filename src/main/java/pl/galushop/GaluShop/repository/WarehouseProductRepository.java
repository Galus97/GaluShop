package pl.galushop.GaluShop.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pl.galushop.GaluShop.entity.WarehouseProduct;

public interface WarehouseProductRepository extends JpaRepository<WarehouseProduct, Long> {

    WarehouseProduct findByProduct_ProductId(Long productId);

    @Transactional
    @Modifying
    @Query("UPDATE WarehouseProduct wp SET wp.quantity = :quantity WHERE wp.product.productId = :productId")
    void updateQuantityByProductId(Long productId, Integer quantity);
}
