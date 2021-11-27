package first.app.microservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class OfficeNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(OfficeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String officeNotFoundHandler(OfficeNotFoundException ex){
        return ex.getMessage();
    }
}
