package pl.galushop.GaluShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.galushop.GaluShop.entity.UserData;

public interface UserDataRepository extends JpaRepository<UserData, Long> {
}
