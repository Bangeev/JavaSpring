package homework.model;

import homework.exception.ConstraintViolation;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
public class ErrorResponseDto {
    private int status;
    private String message;
    private List<ConstraintViolation> violations;
}
