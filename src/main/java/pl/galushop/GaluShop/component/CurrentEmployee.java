package pl.galushop.GaluShop.component;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import pl.galushop.GaluShop.entity.Employee;

@Getter
public class CurrentEmployee extends User {
    private final Employee employee;

    public CurrentEmployee(String username, String password, java.util.Collection<?
            extends GrantedAuthority> authorities, Employee employee){

        super(username, password, authorities);
        this.employee = employee;
    }
}
