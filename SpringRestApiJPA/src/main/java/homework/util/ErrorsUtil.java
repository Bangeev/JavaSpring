package homework.util;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ErrorsUtil {
    public static List<String> getAllErrors(Errors errors) {
        List<String> allErrors = new ArrayList<>();
        allErrors.addAll(getFieldErrors(errors));
        allErrors.addAll(getGlobalErrors(errors));
        return allErrors;
    }

    private static List<String> getGlobalErrors(Errors errors) {
        return errors.getGlobalErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
    }

    public static List<String> getFieldErrors(Errors errors) {
        return errors.getFieldErrors().stream().map(err -> String.format(
                        "Invalid value = %s of field %s. %s",  err.getRejectedValue(),err.getField(), err.getDefaultMessage()))
                .collect(Collectors.toList());
    }
}
