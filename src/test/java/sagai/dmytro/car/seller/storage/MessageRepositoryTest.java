package sagai.dmytro.car.seller.storage;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import sagai.dmytro.car.seller.model.advertisements.Advertisement;
import sagai.dmytro.car.seller.model.advertisements.Message;

import java.sql.Timestamp;
import java.util.Date;
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
public class MessageRepositoryTest {
    private MessageRepository messageRepository;
    private AdvertisementRepository advertisementRepository;


    @Before
    public void init() {
        ApplicationContext context = new FileSystemXmlApplicationContext(
                this.getClass().getClassLoader().getResource("spring-context.xml").getPath()
        );
        this.advertisementRepository = context.getBean("advertisementRepository", AdvertisementRepository.class);
        this.messageRepository = context.getBean("messageRepository", MessageRepository.class);
    }

    @Test
    public void saveUpdateTest() throws Exception {
        Advertisement advertisement = this.advertisementRepository.getAdvertisements().get(0);
        Message message = new Message();
        message.setAdvertisement(advertisement);
        message.setBody("Hello");
        message.setAuthor(advertisement.getOwner());
        message.setCreated(new Timestamp(new Date().getTime()));
        this.messageRepository.saveUpdateMessage(message);

        List<Message> messageList = this.messageRepository.getMessageList(advertisement);
        assertThat(messageList.contains(message), is(true));

        this.messageRepository.removeMessage(message);
        messageList = this.messageRepository.getMessageList(advertisement);
        assertThat(messageList.contains(message), is(false));
    }
}