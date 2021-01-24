package perococco.perobobbot.validation;

import lombok.NonNull;
import perobobbot.validation.ErrorType;
import perobobbot.validation.PathValidator;
import perobobbot.validation.StringValidator;

import java.nio.file.Path;

public class PeroStringValidator extends AbstractValidator<String, StringValidator> implements StringValidator  {


    public PeroStringValidator(@NonNull ValidationContext context,
                               @NonNull String fieldName,
                               String value) {
        super(context, fieldName, value);
    }

    @Override
    protected StringValidator getThis() {
        return this;
    }

    @Override
    public @NonNull StringValidator isNotEmpty() {
        return errorIf(String::isEmpty, ErrorType.NOT_EMPTY_TEXT_REQUIRED);
    }

    @Override
    public @NonNull StringValidator isNotBlank() {
        return errorIf(String::isBlank, ErrorType.NOT_EMPTY_TEXT_REQUIRED);
    }

    @Override
    public @NonNull PathValidator toPathValidator() {
        return map(Path::of, PeroPathValidator::new);
    }

    @Override
    public @NonNull PathValidator toPathValidator(@NonNull PathValidator parent) {
        return map(v -> parent.isAFolder().getValue().resolve(v), PeroPathValidator::new);
    }
}
