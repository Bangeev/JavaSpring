package homework.exception;

import java.util.ArrayList;
import java.util.List;

public class InvalidDataException extends RuntimeException{
    List<String> violations = new ArrayList<>();

    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(List<String> violations) {
        this.violations = violations;
    }

    public InvalidDataException(String message, List<String> violations) {
        super(message);
        this.violations = violations;
    }

    public InvalidDataException(String message, Throwable cause, List<String> violations) {
        super(message, cause);
        this.violations = violations;
    }

    public InvalidDataException(Throwable cause, List<String> violations) {
        super(cause);
        this.violations = violations;
    }

    public InvalidDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, List<String> violations) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.violations = violations;
    }

    public List<String> getViolations() {
        return violations;
    }
}
