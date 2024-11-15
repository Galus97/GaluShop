package pl.galushop.GaluShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.galushop.GaluShop.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
