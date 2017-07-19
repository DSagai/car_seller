package sagai.dmytro.car.seller.storage;

import org.apache.log4j.Logger;

/**
 * Enum encapsulates restriction rules.
 *
 * @author dsagai
 * @version 1.00
 * @since 07.07.2017
 */
public enum RestrictionsEnum {

    GE(" >= "),
    LE(" <= "),
    LIKE(" LIKE "),
    EQ(" = ");

    private static Logger LOGGER = Logger.getLogger(RestrictionsEnum.class);


    private final String restriction;

    /**
     * method returns String representation of restriction.
     * @param restriction String.
     */
    RestrictionsEnum(String restriction) {
        this.restriction = restriction;
    }
    public String getRestriction() {
        return restriction;
    }
}
