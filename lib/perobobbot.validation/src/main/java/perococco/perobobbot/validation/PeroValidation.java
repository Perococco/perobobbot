package perococco.perobobbot.validation;

import lombok.NonNull;
import perobobbot.validation.*;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Callable;

public class PeroValidation implements Validation {

    private final ValidationContext context = new ValidationContext();

    @Override
    public @NonNull <T> Validator<T,?> with(@NonNull String fieldName, T value) {
        return new PeroValidator<>(context, fieldName, value);
    }

    @Override
    public @NonNull <T> Validator<T, ?> with(@NonNull String fieldName, Callable<T> getter) {
        try {
            return with(fieldName,getter.call());
        } catch (Exception e) {
            final PeroValidator<T> validator = new PeroValidator<>(context, fieldName, null);
            return validator.addError(ErrorType.INVALID_VALUE);
        }
    }

    @Override
    public @NonNull StringValidator with(@NonNull String fieldName, String value) {
        return new PeroStringValidator(context, fieldName, value);
    }

    @Override
    public @NonNull PathValidator with(@NonNull String fieldName, Path value) {
        return new PeroPathValidator(context, fieldName, value);
    }

    @Override
    public @NonNull <U> ListValidator<U> with(@NonNull String fieldName, List<U> value) {
        return new PeroListValidator<>(context, fieldName, value);
    }

    @Override
    public boolean isValid() {
        return context.isValid();
    }

    @Override
    public @NonNull ValidationResult getResult() {
        return context.getResult();
    }
}
