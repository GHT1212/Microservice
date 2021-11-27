package first.app.microservice.exception;

public class OfficeNotFoundException extends RuntimeException{

    public OfficeNotFoundException(Long id){
        super("Could Not Find Office" + id);
    }
}
