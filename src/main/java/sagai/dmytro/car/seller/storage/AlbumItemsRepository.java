package sagai.dmytro.car.seller.storage;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sagai.dmytro.car.seller.model.advertisements.Advertisement;
import sagai.dmytro.car.seller.model.advertisements.AlbumItem;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Repository of albumItems
 * @author dsagai
 * @version 1.00
 * @since 26.04.2017
 */
@Repository("albumItemsRepository")
public class AlbumItemsRepository {
    private static final Logger LOGGER = Logger.getLogger(AlbumItemsRepository.class);

    @Autowired
    @Named("sessionFactory")
    private SessionFactory factory;

    public AlbumItemsRepository() {
    }

    /**
     * method adds new AlbumItem into database or updates existing one.
     * @param albumItem AlbumItem.
     * @throws StorageException
     */
    public void saveUpdateAlbumItem(AlbumItem albumItem) throws StorageException {
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        try{
            session.saveOrUpdate(albumItem);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.WARN, e);
            throw new StorageException("can't save/update albumItem into database", e);
        } finally {
            session.close();
        }
    }

    /**
     * method removes AlbumItem from database.
     * @param albumItem AlbumItem.
     * @throws StorageException
     */
    public void removeAlbumItem(AlbumItem albumItem) throws StorageException {
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        try{
            session.remove(albumItem);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.WARN, e);
            throw new StorageException("can't remove albumItem from database", e);
        } finally {
            session.close();
        }
    }

    /**
     * method retrieves list of albumItems by connected advertisement.
     * @param advertisement Advertisement.
     * @return List of albumItems.
     * @throws StorageException
     */
    public List<AlbumItem> getAlbumItems(Advertisement advertisement) throws StorageException {
        List<AlbumItem> result = new ArrayList<>();
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            result = session.createQuery("from AlbumItem where advertisement = :adv")
                    .setParameter("adv", advertisement).list();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.WARN, e);
            throw new StorageException("can't retrieve albumItem list", e);
        } finally {
            session.close();
        }
        return result;
    }

    /**
     * method retrieves albumItems connected to the requested advertisement as array of id's.
     * @param advertisement Advertisement.
     * @return int[] array containing id's of albumItems connected to the advertisement parameter.
     * @throws StorageException
     */
    public Integer[] getAlbumItemIdArray(Advertisement advertisement) throws StorageException {
        Integer[] result = null;
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            List<Integer> resultSet = session.createQuery("select id from AlbumItem a where a.advertisement = :adv")
                    .setParameter("adv", advertisement).list();
            transaction.commit();
            result = resultSet.toArray(new Integer[resultSet.size()]);
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.WARN, e);
            throw new StorageException("can't retrieve array of albumItems", e);
        } finally {
            session.close();
        }
        return result;
    }

    /**
     * method retrieves albumItem by id.
     * @param id int.
     * @return AlbumItem.
     * @throws StorageException
     */
    public AlbumItem getAlbumItem(int id) throws StorageException {
        AlbumItem albumItem = null;
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            albumItem = session.get(AlbumItem.class, id);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.WARN, e);
            throw new StorageException("can't retrieve albumItem from database", e);
        } finally {
            session.close();
        }
        return albumItem;
    }
}
