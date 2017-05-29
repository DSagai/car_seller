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
 * TODO: add comments
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

    public SessionFactory getFactory() {
        return factory;
    }

    public void setFactory(SessionFactory factory) {
        this.factory = factory;
    }

    public void saveUpdateAdvertisement(Advertisement advertisement){
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.saveOrUpdate(advertisement);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.INFO, e.getMessage());

        } finally {
            session.close();
        }

    }

    public void removeAdvertisement(Advertisement advertisement) {
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.remove(advertisement);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.INFO, e.getMessage());
        } finally {
            session.close();
        }
    }

    public List<Advertisement> getAdvertisements(Criterion criterion) {
        Session session = this.factory.openSession();
        Criteria criteria = session.createCriteria(Advertisement.class);
        criteria.add(criterion);

        List<Advertisement> result = new ArrayList<>();
        try {
            criteria.list();
        } catch (Exception e) {
            LOGGER.log(Level.INFO, e.getMessage());
        } finally {
            session.close();
        }

        return result;
    }

    public List<Advertisement> getAdvertisement() {
        List<Advertisement> result = new ArrayList<>();
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            result = session.createQuery("from Advertisement ").list();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
        return result;
    }
}
