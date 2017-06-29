package sagai.dmytro.car.seller.storage;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import sagai.dmytro.car.seller.model.authentication.User;

import javax.inject.Named;
import java.util.UUID;

/**
 * Repository for User class.
 *
 * @author dsagai
 * @version 1.00
 * @since 26.04.2017
 */
@Repository("userRepository")
public class UserRepository {
    private static final Logger LOGGER = Logger.getLogger(UserRepository.class);

    public static final String DUPLICATE_EMAIL = "DUPLICATE_EMAIL";
    public static final String DUPLICATE_LOGIN = "DUPLICATE_LOGIN";

    @Autowired
    @Named("sessionFactory")
    private SessionFactory factory;

    @Autowired
    @Named("passwordEncoder")
    private PasswordEncoder passwordEncoder;

    public UserRepository() {
    }

    /**
     * method adds user into database.
     * @param user User.
     * @throws StorageException
     */
    public void addUser(User user) throws StorageException {
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        try{
            user.setUuid(UUID.randomUUID());
            user.setPassword(this.passwordEncoder.encode(user.getPassword()));
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.WARN, e);
            throw new StorageException("can't add user into database", e);
        } finally {
            session.close();
        }
    }

    /**
     * method retrieves user by id.
     * @param id
     * @return
     * @throws StorageException
     */
    public User getUser(int id) throws StorageException {
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = null;
        try{
            user = session.get(User.class, id);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.WARN, e);
            throw new StorageException("can't retrieve user for id = " + id, e);
        } finally {
            session.close();
        }
        return user;
    }

    /**
     * method retrieves user by login.
     * @param login
     * @return User.
     * @throws StorageException
     */
    public User getUser(String login) throws StorageException {
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = null;
        try {
            user = (User) session.createQuery("from User u where u.username= :login")
                    .setParameter("login", login).uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.WARN, e);
            throw new StorageException("can't retrieve user for login = " + login, e);
        } finally {
            session.close();
        }
        return user;
    }

    /**
     * Method retrieves user by UUID.
     * @param uuid UUID.
     * @return User.
     * @throws StorageException
     */
    public User getUser(UUID uuid) throws StorageException {
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = null;
        try {
            user = (User) session.createQuery("from User u where u.uuid = :uuid")
                    .setParameter("uuid", uuid).uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.WARN, e);
            throw new StorageException("can't retrieve user for uuid = " + uuid, e);
        } finally {
            session.close();
        }
        return user;
    }

    /**
     * method updates an existing user.
     * @param user User.
     * @throws StorageException
     */
    public void updateUser(User user) throws StorageException {
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(user);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.WARN, e);
            throw new StorageException("can't update user", e);
        } finally {
            session.close();
        }
    }

    /**
     * method removes user from database.
     * @param user User.
     * @throws StorageException
     */
    public void removeUser(User user) throws StorageException {
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        try{
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.WARN, e);
            throw new StorageException("can't remove user", e);
        } finally {
            session.close();
        }
    }

    /**
     * Method checks duplicate for requested user in database.
     * Requested user has to have unique values for fields: login and email.
     * Method is used for add-user form verification.
     * @param user User.
     * @return String check result: empty string, if no duplicates were found, or an error message
     * if duplicate was found.
     * @throws StorageException
     */
    public String checkDuplicates(User user) throws StorageException {
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        String result = "";
        try {
            long logins = 0;
            long emails = 0;
            logins = (Long) session.createQuery("select count(*) as count from User u where u.username = :login")
                    .setParameter("login", user.getUsername()).uniqueResult();
            emails = (Long) session.createQuery("select count(*) as count from User u where u.email = :email")
                    .setParameter("email", user.getEmail()).uniqueResult();
            if (logins > 0) {
                result = UserRepository.DUPLICATE_LOGIN;
            } else if (emails > 0) {
                result = UserRepository.DUPLICATE_EMAIL;
            }

        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.WARN, e);
            throw new StorageException("some error happened during duplicate check", e);
        } finally {
            session.close();
        }
        return result;
    }


}
