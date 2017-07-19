package sagai.dmytro.car.seller.storage;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.Transaction;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sagai.dmytro.car.seller.model.advertisements.Advertisement;


import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;


/**
 * Repository of Advertisements.
 *
 * @author dsagai
 * @version 1.00
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
     * Method returns list of Advertisements as response on search request with
     * custom filter (list of SearchAdvParam).
     * @param params SearchAdvParam list
     * @return List<Advertisement>
     * @throws StorageException
     */
    public List<Advertisement> getAdvertisements(List<SearchAdvParam> params) throws StorageException {
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Advertisement> result = new ArrayList<>();
        try {
            StringBuilder stringBuilder = new StringBuilder("from Advertisement where ");
            for (SearchAdvParam searchAdvParam : params) {
                stringBuilder.append(String.format(" %s %s :%s and", searchAdvParam.getFieldName(),
                        searchAdvParam.getRestriction().getRestriction(),
                        searchAdvParam.getFieldName()));
            }
            stringBuilder.replace(stringBuilder.length() - 3, stringBuilder.length(), "");
            Query query = session.createQuery(stringBuilder.toString());
            for (SearchAdvParam searchAdvParam : params) {
                query.setParameter(searchAdvParam.getFieldName(), searchAdvParam.getSearchParamObject());
            }
            result = query.list();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.WARN, e);
            throw new StorageException("can't execute query", e);
        } finally {
            session.close();
        }
        return result;
    }




    /**
     * Method returns all Advertisements containing in database.
     * @return List<Advertisement>
     * @throws StorageException
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

    public List<Advertisement> test(SearchAdvParam searchAdvParam) {
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Advertisement> result = new ArrayList<>();
        try {
            StringBuilder stringBuilder = new StringBuilder("from Advertisement where ");
            stringBuilder.append(String.format(" %s %s :%s ", searchAdvParam.getFieldName(),
                    searchAdvParam.getRestriction().getRestriction(),
                    searchAdvParam.getFieldName()));
            result = session.createQuery(stringBuilder.toString())
                    .setParameter(searchAdvParam.getFieldName(), searchAdvParam.getSearchParamObject()).list();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            session.close();
        }
        return result;
    }
}
