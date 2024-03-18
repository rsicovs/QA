package cucumber.exceptions;

public class UserLockedException extends Throwable {
    public UserLockedException(String errorMessage) {
        super(errorMessage);
    }
}