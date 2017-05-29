package sagai.dmytro.car.seller.controllers;

import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * TODO: add comments
 *
 * @author dsagai
 * @version TODO: set version
 * @since 11.05.2017
 */
@ControllerAdvice
public class DataBaseErrorHandler {

    @ExceptionHandler(DataAccessException.class)
    public String handleDatabaseException(){
        return "error";
    }
}
