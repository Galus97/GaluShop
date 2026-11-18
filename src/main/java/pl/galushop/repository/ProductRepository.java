package pl.galushop.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.galushop.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProductName(String productName);

    List<Product> findAllByCategories(List<String> categories);

    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :priceFrom AND :priceTo")
    List<Product> findAllByPriceBetween(
            @Param("priceFrom") Double priceFrom,
            @Param("priceTo") Double priceTo,
            Pageable pageable);
}
