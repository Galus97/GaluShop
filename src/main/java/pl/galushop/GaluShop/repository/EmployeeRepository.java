package pl.galushop.GaluShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import pl.galushop.GaluShop.entity.Employee;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Employee e SET e.firstName = : firstName, e.lastName = :lastName, " +
            "e.email = :email, e.password = :password WHERE e.employeeId = :employeeId")
    void updateEmployeeByEmployeeId(Long employeeId, String firstName, String lastName, String email, String password);
}
