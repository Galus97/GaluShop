package pl.galushop.GaluShop.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.galushop.GaluShop.model.Order;
import pl.galushop.GaluShop.model.User;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUser_UserId(Long userId);

    Optional<Order> findByOrderIdAndUser(Long orderId, User user);

    List<Order> findAllByUser(User user, Pageable pageable);
}
