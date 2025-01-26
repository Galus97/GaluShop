package pl.galushop.GaluShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import pl.galushop.GaluShop.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByEmail(String email);

    Optional<User> findByUserId(Long userId);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.firstName = :firstName, u.lastName = :lastName, u.email = :email, u.password = :password WHERE u.userId = :userId")
    void updateUserByUserId(Long userId, String firstName, String lastName, String email, String password);

    @Transactional
    @Modifying
    void deleteByUserId(Long userId);
}
