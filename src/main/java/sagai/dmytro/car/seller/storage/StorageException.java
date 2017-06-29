package sagai.dmytro.car.seller.storage;

/**
 * Exception thrown by storage layer
 *
 * @author dsagai
 * @version 1.00
 * @since 29.06.2017
 */

public class StorageException extends Exception {

    public StorageException() {
    }

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }

    public StorageException(Throwable cause) {
        super(cause);
    }
}
