package sg.edu.nus.visa.paf_day24.exception;

public class AmountNotSufficientException extends RuntimeException {
    
    public AmountNotSufficientException(String message) {
        super(message);
    }
}