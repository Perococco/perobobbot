package perobobbot.server.config.validation;

import lombok.NonNull;
import lombok.Value;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import java.util.Optional;

@Value
public class Violation {

    public static @NonNull Violation from(@NonNull ConstraintViolation constraintViolation) {
        return new Violation(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
    }

    public static @NonNull Violation from(@NonNull FieldError fieldError) {
        final var msg = Optional.ofNullable(fieldError.getDefaultMessage()).orElse("unknown error");
        return new Violation(fieldError.getField(), msg);
    }

    @NonNull String fieldName;
    @NonNull String message;
}
