package homework.exception;

import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

public class InvalidConstraintException extends RuntimeException {
    private List<ConstraintViolation> fieldViolations = Collections.emptyList();

    public InvalidConstraintException() {
    }

    public InvalidConstraintException(String message) {
        super(message);
    }

    public InvalidConstraintException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidConstraintException(Throwable cause) {
        super(cause);
    }

    public InvalidConstraintException(List<ConstraintViolation> fieldViolations) {
        this.fieldViolations = fieldViolations;
    }

    public InvalidConstraintException(String message, List<ConstraintViolation> fieldViolations) {
        super(message);
        this.fieldViolations = fieldViolations;
    }

    public InvalidConstraintException(String message, Throwable cause, List<ConstraintViolation> fieldViolations) {
        super(message, cause);
        this.fieldViolations = fieldViolations;
    }

    public InvalidConstraintException(Throwable cause, List<ConstraintViolation> fieldViolations) {
        super(cause);
        this.fieldViolations = fieldViolations;
    }

    public List<ConstraintViolation> getFieldViolations() {
        return fieldViolations;
    }

    public void setFieldViolations(List<ConstraintViolation> fieldViolations) {
        this.fieldViolations = fieldViolations;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", InvalidConstraintException.class.getSimpleName() + "[", "]")
                .add("fieldViolations=" + fieldViolations)
                .add("message='" + getMessage() + "'")
                .add("cause=" + getCause())
                .add("stackTrace=" + super.getStackTrace())
                .toString();
    }
}
