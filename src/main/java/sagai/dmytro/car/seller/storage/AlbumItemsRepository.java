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

    public SessionFactory getFactory() {
        return factory;
    }

    public void setFactory(SessionFactory factory) {
        this.factory = factory;
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
        try{
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
}
