package pl.galushop.GaluShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.galushop.GaluShop.entity.Employee;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);
}
