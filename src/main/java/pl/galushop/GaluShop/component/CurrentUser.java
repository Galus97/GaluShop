package pl.galushop.GaluShop.component;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * Custom implementation of Spring Security's {@link User} class
 * to include additional user details.
 */
@Getter
public class CurrentUser extends User {
    private final pl.galushop.GaluShop.entity.User user;

    /**
     * Constructs a {@code CurrentEmployee} instance with the given details.
     *
     * @param username    The username of the employee.
     * @param password    The encoded password of the employee.
     * @param authorities The granted authorities for the employee.
     * @param user        The employee entity associated with this user.
     */
    public CurrentUser(String username, String password, java.util.Collection<?
            extends GrantedAuthority> authorities, pl.galushop.GaluShop.entity.User user) {

        super(username, password, authorities);
        this.user = user;
    }
}
