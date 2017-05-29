package sagai.dmytro.car.seller.storage;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import sagai.dmytro.car.seller.model.advertisements.attributes.AdvAttribute;
import sagai.dmytro.car.seller.model.advertisements.attributes.AttributeTypes;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Tests for AdvAttributeRepository class
 *
 * @author dsagai
 * @version 1.00
 * @since 25.04.2017
 */
public class AdvAttributeRepositoryTest {
    private AdvAttributeRepository repository;

    @Before
    public void init() throws Exception {
        ApplicationContext context = new FileSystemXmlApplicationContext(
                this.getClass().getClassLoader().getResource("spring-context.xml").getPath()
        );
        this.repository = context.getBean("advAttributeRepository", AdvAttributeRepository.class);
    }

    @Test
    public void whenGetAttributesByTypeThenReceiveThatType() throws Exception {
        AdvAttribute atr1 = new AdvAttribute(AttributeTypes.EngineType, "Type1");
        AdvAttribute atr2 = new AdvAttribute(AttributeTypes.EngineType, "Type2");
        AdvAttribute atr3 = new AdvAttribute(AttributeTypes.CarBodyType, "Type3");

        this.repository.saveUpdateAttribute(atr1);
        this.repository.saveUpdateAttribute(atr2);
        this.repository.saveUpdateAttribute(atr3);

        List<AdvAttribute> advAttributes = this.repository.getAttributesByType(AttributeTypes.EngineType);

        assertThat(advAttributes.contains(atr1), is(true));
        assertThat(advAttributes.contains(atr2), is(true));
        assertThat(advAttributes.contains(atr3), is(false));

        this.repository.removeAttribute(atr1);
        this.repository.removeAttribute(atr2);
        this.repository.removeAttribute(atr3);
    }

    @Test
    public void updateAttribute() throws Exception {
        AdvAttribute atr = new AdvAttribute(AttributeTypes.EngineType, "Type1");
        this.repository.saveUpdateAttribute(atr);

        atr.setName("newName");
        this.repository.saveUpdateAttribute(atr);

        List<AdvAttribute> advAttributes = this.repository.getAttributesByType(atr.getType());

        assertThat(advAttributes.contains(atr), is(true));

        this.repository.removeAttribute(atr);
    }
}