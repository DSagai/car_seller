package sagai.dmytro.car.seller.storage;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import sagai.dmytro.car.seller.model.authentication.SecurityRole;
import sagai.dmytro.car.seller.model.authentication.User;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * TODO: add comments
 *
 * @author dsagai
 * @version TODO: set version
 * @since 15.05.2017
 */
public class UserRepositoryTest {
    private UserRepository userRepository;

    @Before
    public void init() throws Exception {
        ApplicationContext context = new FileSystemXmlApplicationContext(
                this.getClass().getClassLoader().getResource("spring-context.xml").getPath());
        this.userRepository = context.getBean(UserRepository.class);
    }

    @Test
    public void testGetDuplicates() throws Exception {
        User user = new User();
        user.setUsername("test_login");
        user.setEmail("test@email.com");
        user.setPassword("1");
        user.setFirstName("First");
        user.setLastName("Last");
        user.setPhoneNumber("54356");
        user.setRole(SecurityRole.ROLE_USER);
        this.userRepository.addUser(user);

        User duplicateLogin = new User();
        duplicateLogin.setUsername("test_login");
        assertThat(this.userRepository.checkDuplicates(duplicateLogin), is(UserRepository.DUPLICATE_LOGIN));

        User duplicateEmail = new User();
        duplicateEmail.setEmail("test@email.com");
        assertThat(this.userRepository.checkDuplicates(duplicateEmail), is(UserRepository.DUPLICATE_EMAIL));

        User uniqueUser = new User();
        user.setUsername("unique_login");
        user.setEmail("unique_email");
        assertThat(this.userRepository.checkDuplicates(uniqueUser), is(""));

        this.userRepository.removeUser(user);
    }
}