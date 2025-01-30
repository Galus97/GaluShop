package pl.galushop.GaluShop.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pl.galushop.GaluShop.entity.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {


    Optional<Product> findByProductId(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.productName = :productName, p.description = : description, p.price = :price, " +
            "p.category = :category, p.categoryId = : categoryId WHERE p.productId = :productId")
    void updateProductByProductId(Long productId, String productName, String description,
                                  Double price, String category, Integer categoryId);
}
