package sagai.dmytro.car.seller.storage;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
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
 * Repository for Message.
 * @author dsagai
 * @version 1.00
 * @since 26.04.2017
 */
@Repository("messageRepository")
public class MessageRepository {
    private static final Logger LOGGER = Logger.getLogger(MessageRepository.class);

    @Autowired
    @Named("sessionFactory")
    private SessionFactory factory;

    public MessageRepository() {
    }

    /**
     * method adds new message into database or updates existing one.
     * @param message Message.
     * @throws StorageException
     */
    public void saveUpdateMessage(Message message) throws StorageException {
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.saveOrUpdate(message);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.WARN, e);
            throw new StorageException("can't save or update message", e);
        } finally {
            session.close();
        }
    }

    /**
     * method removes message from database.
     * @param message Message.
     * @throws StorageException
     */
    public void removeMessage(Message message) throws StorageException {
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(message);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.WARN, e);
            throw new StorageException("can't remove message", e);
        } finally {
            session.close();
        }
    }

    /**
     * method retrieves list of messages by advertisement.
     * @param advertisement Advertisement.
     * @return List of Message.
     * @throws StorageException
     */
    public List<Message> getMessageList(Advertisement advertisement) throws StorageException {
        List<Message> result = new ArrayList<>();
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            result = session.createQuery("from Message m where m.advertisement = :advertisement order by m.created")
                    .setParameter("advertisement", advertisement).list();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.WARN, e);
            throw new StorageException("can't retrieve message list", e);
        } finally {
            session.close();
        }

        return result;
    }
}
