package perobobbot.validation;

import lombok.NonNull;

public interface StringValidator extends Validator<String,StringValidator> {

    @NonNull
    StringValidator isNotEmpty();

    @NonNull
    StringValidator isNotBlank();

    @Override
    @NonNull
    StringValidator isNotNull();

    @NonNull
    PathValidator toPathValidator();

    @NonNull
    PathValidator toPathValidator(@NonNull PathValidator parent);
}
