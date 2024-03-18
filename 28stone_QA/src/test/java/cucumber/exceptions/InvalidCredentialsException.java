package cucumber.exceptions;

public class InvalidCredentialsException extends Throwable {
    public InvalidCredentialsException(String errorMessage) {
        super(errorMessage);
    }
}
