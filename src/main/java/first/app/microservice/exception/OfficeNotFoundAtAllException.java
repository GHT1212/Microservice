package first.app.microservice.exception;


public class OfficeNotFoundAtAllException extends RuntimeException {

    public OfficeNotFoundAtAllException(){
        super("Could Not Find Office");
    }
}
