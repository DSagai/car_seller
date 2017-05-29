package sagai.dmytro.car.seller.storage;


import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import sagai.dmytro.car.seller.model.advertisements.Advertisement;

import sagai.dmytro.car.seller.model.authentication.User;

import static org.junit.Assert.*;

/**
 * TODO: add comments
 *
 * @author dsagai
 * @version TODO: set version
 * @since 25.04.2017
 */
public class AdvertisementRepositoryTest {
    private AdvertisementRepository repository;

    @Before
    public void init() throws Exception {
        ApplicationContext context = new FileSystemXmlApplicationContext(
                this.getClass().getClassLoader().getResource("spring-context.xml").getPath()
        );
        this.repository = context.getBean("advertisementRepository", AdvertisementRepository.class);
    }

    @Test
    public void whenSaveObjectThenCouldRetrieve() throws Exception {
        Advertisement advertisement = new Advertisement();
        advertisement.setOwner(new User());
        advertisement.setDescription("Description");

    }
}