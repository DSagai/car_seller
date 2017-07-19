package sagai.dmytro.car.seller.storage;


import com.sun.org.apache.regexp.internal.RE;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import sagai.dmytro.car.seller.model.advertisements.Advertisement;

import sagai.dmytro.car.seller.model.advertisements.attributes.AdvAttribute;
import sagai.dmytro.car.seller.model.authentication.User;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
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
    private AdvAttributeRepository advAttributeRepository;

    @Before
    public void init() throws Exception {
        ApplicationContext context = new FileSystemXmlApplicationContext(
                this.getClass().getClassLoader().getResource("spring-context.xml").getPath()
        );
        this.repository = context.getBean("advertisementRepository", AdvertisementRepository.class);
        this.advAttributeRepository = context.getBean("advAttributeRepository", AdvAttributeRepository.class);
    }

    @Test
    public void whenSaveObjectThenCouldRetrieve() throws Exception {
        Advertisement advertisement = new Advertisement();
        advertisement.setOwner(new User());
        advertisement.setDescription("Description");

    }

    @Test
    public void whenRequestAdvListWithEmptyParamsThenReceiveFullList() throws Exception {
        List<SearchAdvParam> params = new ArrayList<>();
        List<Advertisement> advertisements = this.repository.getAdvertisements(params);
        List<Advertisement> fullList = this.repository.getAdvertisements();
        assertThat(advertisements.size(), is(fullList.size()));
    }

    @Test
    public void test() throws Exception {
        SearchAdvParam searchAdvParam = new SearchAdvParam();
        searchAdvParam.setFieldName("engineVolume");
        searchAdvParam.setValue("3000");
        searchAdvParam.setRestriction(RestrictionsEnum.EQ);
        List<Advertisement> advertisements = this.repository.test(searchAdvParam);
        for (Advertisement advertisement : advertisements) {
            System.out.println(advertisement);
        }
    }


}