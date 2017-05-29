package sagai.dmytro.car.seller.storage;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import sagai.dmytro.car.seller.model.advertisements.Advertisement;
import sagai.dmytro.car.seller.model.advertisements.AlbumItem;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * TODO: add comments
 *
 * @author dsagai
 * @version TODO: set version
 * @since 26.04.2017
 */
public class AlbumItemsRepositoryTest {

    private AlbumItemsRepository albumItemsRepository;
    private AdvertisementRepository advertisementRepository;

    @Before
    public void init() throws Exception {
        ApplicationContext context = new FileSystemXmlApplicationContext(
                this.getClass().getClassLoader().getResource("spring-context.xml").getPath());
        this.albumItemsRepository = context.getBean("albumItemsRepository", AlbumItemsRepository.class);
        this.advertisementRepository = context.getBean("advertisementRepository", AdvertisementRepository.class);
    }

    @Test
    public void saveDeleteTest() throws Exception {
        AlbumItem albumItem = new AlbumItem();
        Advertisement advertisement = this.advertisementRepository.getAdvertisement().get(0);
        albumItem.setAdvertisement(advertisement);

        albumItem.setPhoto("Hello world".getBytes());

        this.albumItemsRepository.saveUpdateAlbumItem(albumItem);

        List<AlbumItem> albumItemList = this.albumItemsRepository.getAlbumItems(advertisement);
        assertThat(albumItemList.contains(albumItem), is(true));

        this.albumItemsRepository.removeAlbumItem(albumItem);
        albumItemList = this.albumItemsRepository.getAlbumItems(advertisement);
        assertThat(albumItemList.contains(albumItem), is(false));

    }
}