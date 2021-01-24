package perobobbot.validation;

import lombok.NonNull;

import java.nio.file.Path;

public interface PathValidator extends Validator<Path,PathValidator> {

    @Override
    @NonNull PathValidator isNotNull();

    @NonNull
    PathValidator isAFolder();

    @NonNull
    PathValidator doesNotExist();

    @NonNull
    PathValidator isRegularFile();

    @NonNull
    PathValidator resolve(@NonNull StringValidator stringValidator);
}
