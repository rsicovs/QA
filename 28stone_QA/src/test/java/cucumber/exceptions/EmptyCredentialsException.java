package cucumber.exceptions;

public class EmptyCredentialsException extends Throwable {
    public EmptyCredentialsException(String errorMessage) {
        super(errorMessage);
    }
}