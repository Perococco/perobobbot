package perobobbot.server.config.validation;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import perobobbot.lang.fp.Function1;

import javax.validation.ConstraintViolationException;
import java.util.Collection;

@ControllerAdvice
class ErrorHandlingControllerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onConstraintValidationException(
            ConstraintViolationException e) {
        return createResponse(e.getConstraintViolations(),Violation::from);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        return createResponse(e.getFieldErrors(),Violation::from);
    }

    private <T> @NonNull ValidationErrorResponse createResponse(@NonNull Collection<T> errors, @NonNull Function1<? super T, ? extends Violation> violationFactory) {
        final var violations = errors
                .stream()
                .map(violationFactory)
                .map(v -> (Violation) v)
                .collect(ImmutableList.toImmutableList());
        var validationErrorResponse = new ValidationErrorResponse(violations);
        return validationErrorResponse;
    }

}
