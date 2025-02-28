package pl.galushop.GaluShop.component;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import pl.galushop.GaluShop.entity.Employee;

/**
 * Custom implementation of Spring Security's {@link User} class
 * to include additional employee details.
 */
@Getter
public class CurrentEmployee extends User {
    private final Employee employee;

    /**
     * Constructs a {@code CurrentEmployee} instance with the given details.
     *
     * @param username    The username of the employee.
     * @param password    The encoded password of the employee.
     * @param authorities The granted authorities for the employee.
     * @param employee    The employee entity associated with this user.
     */
    public CurrentEmployee(String username, String password, java.util.Collection<?
            extends GrantedAuthority> authorities, Employee employee) {

        super(username, password, authorities);
        this.employee = employee;
    }
}
