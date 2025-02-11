package pl.galushop.GaluShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import pl.galushop.GaluShop.entity.UserData;

import java.util.Optional;

public interface UserDataRepository extends JpaRepository<UserData, Long> {

    Optional<UserData> findByUser_UserId(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE UserData ud SET ud.city = :city, ud.street = :street, ud.streetNumber = :streetNumber , " +
            "ud.apartmentNumber = :apartmentNumber, ud.zipCode = :zipCode, ud.phoneNumber = :phoneNumber " +
            "WHERE ud.userDataId = :userDataId")
    void updateByUserDataId(Long userDataId, String city, String street, Integer streetNumber,
                             Integer apartmentNumber, String zipCode, Integer phoneNumber);
}
