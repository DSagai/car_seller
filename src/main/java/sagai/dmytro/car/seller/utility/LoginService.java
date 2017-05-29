package sagai.dmytro.car.seller.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import sagai.dmytro.car.seller.model.authentication.User;
import sagai.dmytro.car.seller.storage.UserRepository;

import javax.inject.Named;
import java.util.Arrays;
import java.util.Collection;


/**
 * TODO: add comments
 *
 * @author dsagai
 * @version TODO: set version
 * @since 24.05.2017
 */
@Component("loginService")
public class LoginService implements UserDetailsService {
    private final static String NOT_FOUND_EXCEPTION = "User with login %s was not found.";

    @Autowired
    @Named("userRepository")
    private UserRepository userRepository;

    public LoginService() {
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = this.userRepository.getUser(login);
        if (user == null) {
            throw new UsernameNotFoundException(String.format(NOT_FOUND_EXCEPTION, login));
        }
        Collection<GrantedAuthority> authorities = Arrays.asList(user.getRole());
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),
                user.isEnabled(), true, true, true, authorities);
    }
}
