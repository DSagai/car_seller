package sagai.dmytro.car.seller.storage;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import sagai.dmytro.car.seller.model.advertisements.Advertisement;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class represents search criteria for
 * for filtering Advertisement list
 *
 * @author dsagai
 * @version 1.00
 * @since 07.07.2017
 */
@JsonAutoDetect
public final class SearchAdvParam {

    private static final Logger LOGGER = Logger.getLogger(SearchAdvParam.class);

    private static Map<String, Method> ADVERTISEMENT_FIELDS_TYPES = new HashMap<>();

    static {
        try {
            Field[] fields = Advertisement.class.getDeclaredFields();
            for (Field field : fields) {
                String typeName = field.getType().getSimpleName();
                switch (typeName) {
                    case "String":
                        ADVERTISEMENT_FIELDS_TYPES.put(field.getName(),
                                field.getType().getDeclaredMethod("valueOf", Object.class));
                        break;
                    case "int":
                        ADVERTISEMENT_FIELDS_TYPES.put(field.getName(),
                        Integer.class.getDeclaredMethod("parseInt", String.class));
                        break;
                    case "byte":
                        ADVERTISEMENT_FIELDS_TYPES.put(field.getName(),
                                Byte.class.getDeclaredMethod("parseByte", String.class));
                        break;
                    case "short":
                        ADVERTISEMENT_FIELDS_TYPES.put(field.getName(),
                                Short.class.getDeclaredMethod("parseShort", String.class));
                        break;
                    case "long":
                        ADVERTISEMENT_FIELDS_TYPES.put(field.getName(),
                                Long.class.getDeclaredMethod("parseLong", String.class));
                        break;
                    case "float":
                        ADVERTISEMENT_FIELDS_TYPES.put(field.getName(),
                                Float.class.getDeclaredMethod("parseFloat", String.class));
                        break;
                    case "double":
                        ADVERTISEMENT_FIELDS_TYPES.put(field.getName(),
                                Double.class.getDeclaredMethod("parseDouble", String.class));
                        break;
                    case "boolean":
                        ADVERTISEMENT_FIELDS_TYPES.put(field.getName(),
                                Boolean.class.getDeclaredMethod("parseBoolean", String.class));
                        break;
                    default:
                        for (Method method : field.getType().getDeclaredMethods()) {
                            if (method.getName().equals("getInstanceByString")
                                    && method.getGenericParameterTypes().length == 1) {
                                ADVERTISEMENT_FIELDS_TYPES.put(field.getName(), method);
                                break;
                            }
                        }
                        break;
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.FATAL, e);
            throw new RuntimeException("Can't start class SearchAdvParam!", e);
        }
    }

    private String fieldName;
    private RestrictionsEnum restriction;
    private String value;


    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public RestrictionsEnum getRestriction() {
        return restriction;
    }

    public void setRestriction(RestrictionsEnum restriction) {
        this.restriction = restriction;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * method returns Criterion object, which is used for making variable filter.
     * @return Criterion.
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
//    public Criterion getCriterion() throws InvocationTargetException, IllegalAccessException {
//        return this.restriction.getCriterion(this.fieldName, this.value);
//    }

    public Object getSearchParamObject() throws InvocationTargetException, IllegalAccessException {
        Method method = ADVERTISEMENT_FIELDS_TYPES.get(fieldName);
        return method.invoke(null, this.value);
    }


}
