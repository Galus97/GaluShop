package pl.galushop.GaluShop.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.galushop.GaluShop.entity.OrderProduct;
import pl.galushop.GaluShop.entity.OrderProductId;

public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {
}
