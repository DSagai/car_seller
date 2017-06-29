package sagai.dmytro.car.seller.utility;

/**
 * Exception thrown by objects of utility package.
 *
 * @author dsagai
 * @version 1.00
 * @since 29.06.2017
 */

public class UtilityServiceException extends Exception {
    public UtilityServiceException() {
    }

    public UtilityServiceException(String message) {
        super(message);
    }

    public UtilityServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UtilityServiceException(Throwable cause) {
        super(cause);
    }
}
