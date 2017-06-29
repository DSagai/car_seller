package sagai.dmytro.car.seller.storage;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sagai.dmytro.car.seller.model.advertisements.Advertisement;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: add comments, implement filter getAdvertisements methods
 *
 * @author dsagai
 * @version TODO: set version
 * @since 25.04.2017
 */
@Repository("advertisementRepository")
public class AdvertisementRepository {
    private static final Logger LOGGER = Logger.getLogger(AdvertisementRepository.class);

    @Autowired
    @Named("sessionFactory")
    private SessionFactory factory;

    public AdvertisementRepository() {
    }


    /**
     * method adds new advertisement to the database or updates existing one.
     * @param advertisement Advertisement.
     * @throws StorageException
     */
    public void saveUpdateAdvertisement(Advertisement advertisement) throws StorageException {
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.saveOrUpdate(advertisement);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.WARN, e);
            throw new StorageException("advertisement save/update was unsuccessful", e);
        } finally {
            session.close();
        }

    }

    /**
     * method removes advertisement from database.
     * @param advertisement Advertisement.
     * @throws StorageException
     */
    public void removeAdvertisement(Advertisement advertisement) throws StorageException {
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.remove(advertisement);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.WARN, e);
            throw new StorageException("advertisement remove was unsuccessful", e);
        } finally {
            session.close();
        }
    }

    /**
     * TODO
     * @param criterion
     * @return
     */
    public List<Advertisement> getAdvertisements(Criterion criterion) throws StorageException {
        Session session = this.factory.openSession();
        Criteria criteria = session.createCriteria(Advertisement.class);
        criteria.add(criterion);

        List<Advertisement> result = new ArrayList<>();
        try {
            criteria.list();
        } catch (Exception e) {
            LOGGER.log(Level.WARN, e);
            throw new StorageException("can not retrieve advertisements from database", e);
        } finally {
            session.close();
        }

        return result;
    }

    /**
     * TODO
     * @return
     */
    public List<Advertisement> getAdvertisements() throws StorageException {
        List<Advertisement> result = new ArrayList<>();
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            result = session.createQuery("from Advertisement ").list();
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.WARN, e);
            throw new StorageException("can not retrieve advertisements from database", e);

        } finally {
            session.close();
        }
        return result;
    }

    /**
     * method retrieves advertisement from the database by id.
     * @param id int.
     * @return Advertisement.
     * @throws StorageException
     */
    public Advertisement getAdvertisement(int id) throws StorageException {
        Advertisement advertisement = null;
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            advertisement = session.get(Advertisement.class, id);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.WARN, e);
            throw new StorageException("can not retrieve advertisement from database", e);
        } finally {
            session.close();
        }
        return advertisement;
    }
}
