package perobobbot.validation;

import lombok.NonNull;
import perococco.perobobbot.validation.ValidationContext;

public interface ValidatorFactory<T,V extends Validator<T,V>> {

    @NonNull
    V create(@NonNull ValidationContext context, @NonNull String fieldName, T value);

}
