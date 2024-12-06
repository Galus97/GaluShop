package pl.galushop.GaluShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.galushop.GaluShop.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
