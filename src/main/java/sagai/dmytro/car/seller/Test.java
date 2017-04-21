package sagai.dmytro.car.seller;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import sagai.dmytro.car.seller.model.advertisements.Advertisement;
import sagai.dmytro.car.seller.model.authentication.SecurityRole;
import sagai.dmytro.car.seller.model.authentication.User;

import java.util.Arrays;
import java.util.List;

/**
 * TODO: add comments
 *
 * @author dsagai
 * @version TODO: set version
 * @since 19.04.2017
 */
public class Test {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();



        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        SecurityRole role = new SecurityRole("admin");

        User user = new User();


        try {

            session.save(role);
            user.setRole(role);

            user.setEmail("AA@sdd.dd");
            user.setFirstName("first");
            user.setLastName("last");
            user.setLogin("login");
            user.setPassword("password");
            user.setPhoneNumber("phone number");
//            List<Advertisement> advertisementList = Arrays.asList(new Advertisement("adv1"),
//                    new Advertisement("adv2"));



            //user.setAdvertisements(advertisementList);
            session.save(user);
//            for (Advertisement advertisement : advertisementList) {
//                advertisement.setOwner(user);
//                session.save(advertisement);
//            }


            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            factory.close();
        }
        System.out.println("FFF");


    }
}
