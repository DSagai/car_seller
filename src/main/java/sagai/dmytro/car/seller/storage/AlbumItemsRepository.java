package sagai.dmytro.car.seller.storage;

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
 * TODO: add comments
 * todo add logger
 * @author dsagai
 * @version TODO: set version
 * @since 26.04.2017
 */
@Repository("albumItemsRepository")
public class AlbumItemsRepository {
    @Autowired
    @Named("sessionFactory")
    private SessionFactory factory;

    public AlbumItemsRepository() {
    }


    public void saveUpdateAlbumItem(AlbumItem albumItem) {
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        try{
            session.saveOrUpdate(albumItem);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void removeAlbumItem(AlbumItem albumItem) {
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        try{
            session.remove(albumItem);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public List<AlbumItem> getAlbumItems(Advertisement advertisement) {
        List<AlbumItem> result = new ArrayList<>();
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            result = session.createQuery("from AlbumItem where advertisement = :adv")
                    .setParameter("adv", advertisement).list();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

    public Integer[] getAlbumItemIdArray(Advertisement advertisement) {
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
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

    public AlbumItem getAlbumItem(int id) {
        AlbumItem albumItem = null;
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            albumItem = session.get(AlbumItem.class, id);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return albumItem;
    }
}
