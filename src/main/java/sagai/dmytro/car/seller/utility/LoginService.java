package sagai.dmytro.car.seller.utility;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import sagai.dmytro.car.seller.model.authentication.User;
import sagai.dmytro.car.seller.storage.StorageException;
import sagai.dmytro.car.seller.storage.UserRepository;

import javax.inject.Named;
import java.util.Arrays;
import java.util.Collection;


/**
 * Login service for spring-security
 *
 * @author dsagai
 * @version 1.00
 * @since 24.05.2017
 */
@Component("loginService")
public class LoginService implements UserDetailsService {
    private static final Logger LOGGER = Logger.getLogger(LoginService.class);
    private final static String NOT_FOUND_EXCEPTION = "User with login %s was not found.";

    @Autowired
    @Named("userRepository")
    private UserRepository userRepository;

    public LoginService() {
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        try {
            User user = this.userRepository.getUser(login);
            Collection<GrantedAuthority> authorities = Arrays.asList(user.getRole());
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                    user.isEnabled(), true, true, true, authorities);
        } catch (StorageException e) {
            LOGGER.log(Level.WARN, e);
            throw new UsernameNotFoundException(String.format(NOT_FOUND_EXCEPTION, login), e);
        }
    }
}
