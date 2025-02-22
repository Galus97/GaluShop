package pl.galushop.GaluShop.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.galushop.GaluShop.entity.OrderProduct;
import pl.galushop.GaluShop.entity.OrderProductId;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {
    List<OrderProduct> findByOrderOrderId(Long orderId);
    List<OrderProduct> findByProductProductId(Long productId);
}
