package sagai.dmytro.car.seller.storage;

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
 * TODO: add comments
 *
 * @author dsagai
 * @version TODO: set version
 * @since 26.04.2017
 */
@Repository("userRepository")
public class UserRepository {
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

    public void addUser(User user) {
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        try{
            user.setUuid(UUID.randomUUID());
            user.setPassword(this.passwordEncoder.encode(user.getPassword()));
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public User getUser(int id) {
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = null;
        try{
            user = session.get(User.class, id);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return user;
    }

    public User getUser(String login) {
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = null;
        try {
            user = (User) session.createQuery("from User u where u.username= :login")
                    .setParameter("login", login).uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return user;
    }

    public User getUser(UUID uuid) {
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = null;
        try {
            user = (User) session.createQuery("from User u where u.uuid = :uuid")
                    .setParameter("uuid", uuid).uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return user;
    }

    public void updateUser(User user) {
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(user);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void removeUser(User user) {
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        try{
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public String checkDuplicates(User user) {
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
            throw e;
        } finally {
            session.close();
        }
        return result;
    }


}
