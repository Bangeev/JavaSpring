package homework.exception;

import java.util.StringJoiner;

public class ConstraintViolation {
    private String type;
    private String field;
    private Object invalidValue;
    private String errorMessage;

    public ConstraintViolation() {
    }

    public ConstraintViolation(String type, String field, Object invalidValue, String message) {
        this.type = type;
        this.field = field;
        this.invalidValue = invalidValue;
        this.errorMessage = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getInvalidValue() {
        return invalidValue;
    }

    public void setInvalidValue(Object invalidValue) {
        this.invalidValue = invalidValue;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConstraintViolation)) return false;

        ConstraintViolation that = (ConstraintViolation) o;

        if (getType() != null ? !getType().equals(that.getType()) : that.getType() != null) return false;
        if (getField() != null ? !getField().equals(that.getField()) : that.getField() != null) return false;
        if (getInvalidValue() != null ? !getInvalidValue().equals(that.getInvalidValue()) : that.getInvalidValue() != null)
            return false;
        return getErrorMessage() != null ? getErrorMessage().equals(that.getErrorMessage()) : that.getErrorMessage() == null;
    }

    @Override
    public int hashCode() {
        int result = getType() != null ? getType().hashCode() : 0;
        result = 31 * result + (getField() != null ? getField().hashCode() : 0);
        result = 31 * result + (getInvalidValue() != null ? getInvalidValue().hashCode() : 0);
        result = 31 * result + (getErrorMessage() != null ? getErrorMessage().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ConstraintViolation.class.getSimpleName() + "[", "]")
                .add("type='" + type + "'")
                .add("field='" + field + "'")
                .add("invalidValue=" + invalidValue)
                .add("errorMessage='" + errorMessage + "'")
                .toString();
    }
}
