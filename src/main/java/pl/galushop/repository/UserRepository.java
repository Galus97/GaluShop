package pl.galushop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.galushop.GaluShop.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
