package sagai.dmytro.car.seller.controllers;

/**
 * TODO: add comments
 *
 * @author dsagai
 * @version TODO: set version
 * @since 29.06.2017
 */

public class ControllerException extends Exception{
    public ControllerException() {
    }

    public ControllerException(String message) {
        super(message);
    }

    public ControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ControllerException(Throwable cause) {
        super(cause);
    }
}
