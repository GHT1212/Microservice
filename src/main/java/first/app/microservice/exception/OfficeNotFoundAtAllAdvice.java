package first.app.microservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class OfficeNotFoundAtAllAdvice {

    @ResponseBody
    @ExceptionHandler(OfficeNotFoundAtAllException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String officeNotFoundHandler(OfficeNotFoundAtAllException ex){
        return ex.getMessage();
    }
}
