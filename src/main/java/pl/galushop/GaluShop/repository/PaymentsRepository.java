package pl.galushop.GaluShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.galushop.GaluShop.entity.Payments;

import java.util.Optional;

public interface PaymentsRepository extends JpaRepository<Payments, Long> {
    Optional<Payments> findByOrder_OrderId(Long orderId);
}
