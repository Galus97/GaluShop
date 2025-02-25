package pl.galushop.GaluShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.galushop.GaluShop.entity.Payments;

public interface PaymentsRepository extends JpaRepository<Payments, Long> {
}
