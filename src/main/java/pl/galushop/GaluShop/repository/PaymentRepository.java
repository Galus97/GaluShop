package pl.galushop.GaluShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.galushop.GaluShop.model.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrder_OrderId(Long orderId);

    List<Payment> findAllByUser_UserId(Long userId);
}
