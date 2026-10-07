package pl.galushop.GaluShop.repository;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.galushop.GaluShop.model.*;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {
    List<OrderProduct> findByOrder_OrderId(Long orderId);

    List<OrderProduct> findByProduct_ProductId(Long productId);

    List<OrderProduct> findAllByOrderAndUser(Order order, User user, Pageable pageable);

    List<OrderProduct> findAllByProductAndUser(Product product, User user, Pageable pageable);
}
