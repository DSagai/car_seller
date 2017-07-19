package sagai.dmytro.car.seller.storage;

import org.junit.Test;
import sagai.dmytro.car.seller.model.advertisements.attributes.AdvAttribute;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * TODO: add comments
 *
 * @author dsagai
 * @version TODO: set version
 * @since 18.07.2017
 */
public class SearchAdvParamTest {

    @Test
    public void ifStringFieldThenStringSearchObject() throws Exception {
        SearchAdvParam param = new SearchAdvParam();
        param.setFieldName("description");
        String value = "test";
        param.setValue(value);
        Object response = param.getSearchParamObject();
        assertThat(response instanceof String, is(true));
        assertThat(value.equals(response), is(true));
    }

    @Test
    public void ifIntFieldThenIntSearchObject() throws Exception {
        String paramValue = "1";
        SearchAdvParam param = new SearchAdvParam();
        param.setFieldName("engineVolume");
        param.setValue(paramValue);

        int engineVolume = 1;


        Object response = param.getSearchParamObject();
        assertThat(response instanceof Integer, is(true));
        assertThat(response.equals(engineVolume), is(true));
    }

    @Test
    public void ifAdvAttributeFieldThenAdvAttributeSearchParam() throws Exception {
        String paramValue = "1";
        SearchAdvParam param = new SearchAdvParam();
        param.setFieldName("engineType");
        param.setValue(paramValue);

        AdvAttribute attribute = new AdvAttribute();
        attribute.setId(1);

        Object response = param.getSearchParamObject();
        assertThat(response instanceof AdvAttribute, is(true));
        assertThat(attribute.equals(response), is(true));
    }
}