package sagai.dmytro.car.seller;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import sagai.dmytro.car.seller.model.authentication.User;

/**
 * TODO: add comments
 *
 * @author dsagai
 * @version TODO: set version
 * @since 20.04.2017
 */
public class Test2 {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();


        Transaction transaction = session.beginTransaction();

        try {
            User user = session.createQuery("from User u where u.id = :id", User.class)
                    .setParameter("id", 1).getSingleResult();

            transaction.commit();
            System.out.println(user.getAdvertisements().size());
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
            factory.close();
        }

    }
}
