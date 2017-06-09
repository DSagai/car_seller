package sagai.dmytro.car.seller.controllers;

import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * TODO: add comments
 *
 * @author dsagai
 * @version TODO: set version
 * @since 11.05.2017
 */
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(DataAccessException.class)
    public String handleDatabaseException(){
        return "error";
    }

    @ExceptionHandler(FileUploadBase.SizeLimitExceededException.class)
    public ModelAndView handleMaxUploadSizeExceededException(FileUploadBase.SizeLimitExceededException e) {
        ModelAndView mv = new ModelAndView("error");
        Map<String,Object> model = mv.getModel();
        model.put("message", e.getMessage());
        return mv;

    }
}
