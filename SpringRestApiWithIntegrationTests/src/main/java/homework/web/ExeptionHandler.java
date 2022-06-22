package homework.web;




import homework.exception.ConstraintViolation;
import homework.exception.InvalidConstraintException;
import homework.exception.InvalidEntityDataException;
import homework.exception.NonexistingEntityException;
import homework.model.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;
@ControllerAdvice(basePackageClasses = ExceptionHandler.class)
public class ExeptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> handleNonexistingEntityException(NonexistingEntityException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDto(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null));

    }


    @ExceptionHandler({InvalidEntityDataException.class, InvalidConstraintException.class})
    public ResponseEntity<ErrorResponseDto> handleInvalidEntityDataException(Exception e) {
        Throwable ex = e;
        List<ConstraintViolation> violations = null;
        while (ex != null && !(ex instanceof InvalidConstraintException)) {
            ex = ex.getCause();
        }
        if (ex != null) {
            violations = ((InvalidConstraintException) ex).getFieldViolations();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessage(), violations));
    }
}
