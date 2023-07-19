package sg.edu.nus.visa.paf_day24.exception;

public class AccountBlockedAndDisabledException extends RuntimeException{

    public AccountBlockedAndDisabledException(String message) {
        super(message);
    }

}
