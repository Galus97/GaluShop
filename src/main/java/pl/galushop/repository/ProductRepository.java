package pl.galushop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.galushop.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
