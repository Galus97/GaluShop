package pl.galushop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.galushop.model.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProductName(String productName);
}
