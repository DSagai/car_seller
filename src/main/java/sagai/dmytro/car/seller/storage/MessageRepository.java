package sagai.dmytro.car.seller.storage;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sagai.dmytro.car.seller.model.advertisements.Advertisement;
import sagai.dmytro.car.seller.model.advertisements.Message;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: add comments
 * TODO add logger
 * @author dsagai
 * @version TODO: set version
 * @since 26.04.2017
 */
@Repository("messageRepository")
public class MessageRepository {
    @Autowired
    @Named("sessionFactory")
    private SessionFactory factory;

    public MessageRepository() {
    }

    public SessionFactory getFactory() {
        return factory;
    }

    public void setFactory(SessionFactory factory) {
        this.factory = factory;
    }

    public void saveUpdateMessage(Message message) {
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.saveOrUpdate(message);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    public void removeMessage(Message message) {
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(message);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    public List<Message> getMessageList(Advertisement advertisement) {
        List<Message> result = new ArrayList<>();
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            result = session.createQuery("from Message m where m.advertisement = :advertisement order by m.created")
                    .setParameter("advertisement", advertisement).list();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }

        return result;
    }
}
