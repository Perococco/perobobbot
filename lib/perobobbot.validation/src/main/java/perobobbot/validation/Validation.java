package perobobbot.validation;

import lombok.NonNull;
import perococco.perobobbot.validation.PeroValidation;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Callable;

public interface Validation {

    @NonNull
    static Validation create() {
        return new PeroValidation();
    }

    @NonNull
    <T> Validator<T,?> with(@NonNull String fieldName, Callable<T> getter);

    @NonNull
    <T> Validator<T,?> with(@NonNull String fieldName, T value);

    @NonNull
    StringValidator with(@NonNull String fieldName, String value);

    @NonNull
    PathValidator with(@NonNull String fieldName, Path value);

    @NonNull
    <U> ListValidator<U> with(@NonNull String fieldName, List<U> value);


    boolean isValid();

    @NonNull
    ValidationResult getResult();


}
