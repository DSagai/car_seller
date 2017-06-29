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

    /**
     * method saves or updates advAttribute into database.
     * @param advAttribute AdvAttribute.
     * @throws StorageException
     */
    public void saveUpdateAttribute(AdvAttribute advAttribute) throws StorageException {
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.saveOrUpdate(advAttribute);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.WARN, e);
            throw new StorageException("can not save or update attribute", e);
        }
    }

    /**
     * method removes AdvAttribute from data base.
     * @param advAttribute AdvAttribute.
     * @throws StorageException
     */
    public void removeAttribute(AdvAttribute advAttribute) throws StorageException {
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.remove(advAttribute);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.WARN, e);
            throw new StorageException("can not remove attribute from database", e);
        }
    }

    /**
     * method retrieves advertisement attributes by type.
     * @param type AttributeTypes.
     * @return List of AdvAttribute.
     * @throws StorageException
     */
    public List<AdvAttribute> getAttributesByType(AttributeTypes type) throws StorageException {
        List<AdvAttribute> result = new ArrayList<>();
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            result = session.createQuery("from AdvAttribute a where a.type = :type")
                    .setParameter("type", type).list();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.WARN, e);
            throw new StorageException("can't retrieve attributes of type " + type.name(), e);
        } finally {
            session.close();
        }
        return result;
    }

    /**
     * method retrieves attribute from database by attribute name.
     * @param attributeName String.
     * @return AdvAttribute.
     * @throws StorageException
     */
    public AdvAttribute getAttributeByName(String attributeName) throws StorageException {
        AdvAttribute result = null;
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            result = (AdvAttribute) session.createQuery("from AdvAttribute a where a.name = :name")
                    .setParameter("name", attributeName).getSingleResult();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.log(Level.WARN, e);
            throw new StorageException("can't retrieve attribute by name=" + attributeName, e);
        } finally {
            session.close();
        }
        return result;
    }

    /**
     * method retrieves from database an attribute which represents default advertisement status.
     * @return AdvAttribute default advertisement status.
     * @throws StorageException
     */
    public AdvAttribute getDefaultStatus() throws StorageException {
        return getAttributeByName(DEFAULT_STATUS_NAME);
    }
}
