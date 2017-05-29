package sagai.dmytro.car.seller.model.authentication;

import org.springframework.security.core.GrantedAuthority;

/**
 * TODO: add comments
 *
 * @author dsagai
 * @version TODO: set version
 * @since 15.05.2017
 */
public enum  SecurityRole implements GrantedAuthority {
    ROLE_ADMIN,
    ROLE_USER;


    @Override
    public String getAuthority() {
        return this.name();
    }
}
