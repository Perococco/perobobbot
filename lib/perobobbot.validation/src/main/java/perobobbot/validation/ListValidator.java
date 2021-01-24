package perobobbot.validation;

import lombok.NonNull;
import perobobbot.lang.fp.Predicate1;

import java.util.List;


public interface ListValidator<T> extends Validator<List<T>, ListValidator<T>> {

    @Override
    @NonNull ListValidator<T> isNotNull();

    @NonNull
    ListValidator<T> isNotEmpty();

    @Override
    @NonNull
    ListValidator<T> errorIfNot(@NonNull Predicate1<List<T>> test, @NonNull String errorType);
}
