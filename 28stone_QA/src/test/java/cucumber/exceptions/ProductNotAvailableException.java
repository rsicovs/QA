package cucumber.exceptions;

public class ProductNotAvailableException extends Throwable {
    public ProductNotAvailableException(String errorMessage){
        super(errorMessage);
    }
}
