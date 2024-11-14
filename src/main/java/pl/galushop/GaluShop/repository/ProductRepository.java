package pl.galushop.GaluShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.galushop.GaluShop.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
