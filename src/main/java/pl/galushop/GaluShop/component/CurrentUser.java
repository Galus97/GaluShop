package pl.galushop.GaluShop.component;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
public class CurrentUser extends User {
    private final pl.galushop.GaluShop.entity.User user;

    public CurrentUser(String username, String password, java.util.Collection<?
            extends GrantedAuthority> authorities, pl.galushop.GaluShop.entity.User user) {

        super(username, password, authorities);
        this.user = user;
    }
}
