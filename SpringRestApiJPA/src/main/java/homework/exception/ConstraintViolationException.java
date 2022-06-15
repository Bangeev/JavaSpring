package homework.exception;

import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

public class ConstraintViolationException extends Exception {
    private List<ConstraintViolation> fieldViolations = Collections.emptyList();

    public ConstraintViolationException() {
    }

    public ConstraintViolationException(String message) {
        super(message);
    }

    public ConstraintViolationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConstraintViolationException(Throwable cause) {
        super(cause);
    }

    public ConstraintViolationException(List<ConstraintViolation> fieldViolations) {
        this.fieldViolations = fieldViolations;
    }

    public ConstraintViolationException(String message, List<ConstraintViolation> fieldViolations) {
        super(message);
        this.fieldViolations = fieldViolations;
    }

    public ConstraintViolationException(String message, Throwable cause, List<ConstraintViolation> fieldViolations) {
        super(message, cause);
        this.fieldViolations = fieldViolations;
    }

    public ConstraintViolationException(Throwable cause, List<ConstraintViolation> fieldViolations) {
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
        return new StringJoiner(", ", ConstraintViolationException.class.getSimpleName() + "[", "]")
                .add("fieldViolations=" + fieldViolations)
                .add("message='" + getMessage() + "'")
                .add("cause=" + getCause())
                .add("stackTrace=" + super.getStackTrace())
                .toString();
    }
}
