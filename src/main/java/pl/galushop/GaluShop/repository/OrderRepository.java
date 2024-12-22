package pl.galushop.GaluShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.galushop.GaluShop.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
