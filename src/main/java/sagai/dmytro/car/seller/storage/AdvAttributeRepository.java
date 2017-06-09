package sagai.dmytro.car.seller.storage;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sagai.dmytro.car.seller.model.advertisements.attributes.AdvAttribute;
import sagai.dmytro.car.seller.model.advertisements.attributes.AttributeTypes;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository of AdvAttribute entities.
 *
 * @author dsagai
 * @version 1.00
 * @since 25.04.2017
 */
@Repository("advAttributeRepository")
public class AdvAttributeRepository {
    private static final Logger LOGGER = Logger.getLogger(AdvAttributeRepository.class);
    private static final String DEFAULT_STATUS_NAME = "draft";

    @Autowired
    @Named("sessionFactory")
    private SessionFactory factory;

    public AdvAttributeRepository() {
    }

    public SessionFactory getFactory() {
        return factory;
    }

    public void setFactory(SessionFactory factory) {
        this.factory = factory;
    }

    public void saveUpdateAttribute(AdvAttribute advAttribute) {
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.saveOrUpdate(advAttribute);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.INFO, e.getMessage());
        }
    }


    public void removeAttribute(AdvAttribute advAttribute) {
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.remove(advAttribute);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.INFO, e.getMessage());
        }
    }

    public List<AdvAttribute> getAttributesByType(AttributeTypes type) {
        List<AdvAttribute> result = new ArrayList<>();
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            result = session.createQuery("from AdvAttribute a where a.type = :type")
                    .setParameter("type", type).list();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.INFO, e.getMessage());
        } finally {
            session.close();
        }
        return result;
    }

    public AdvAttribute getAttributeByName(String attributeName) {
        AdvAttribute result = null;
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            result = (AdvAttribute) session.createQuery("from AdvAttribute a where a.name = :name")
                    .setParameter("name", attributeName).getSingleResult();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.INFO, e.getMessage());
        } finally {
            session.close();
        }
        return result;
    }

    public AdvAttribute getDefaultStatus() {
        return getAttributeByName(DEFAULT_STATUS_NAME);
    }
}
