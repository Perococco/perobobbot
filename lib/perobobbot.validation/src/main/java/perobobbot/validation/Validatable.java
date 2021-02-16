package perobobbot.validation;

import lombok.NonNull;

public interface Validatable {

    @NonNull
    Validation validate(@NonNull Validation validation);

    default void validateAndCheck() {
        validateAndCheck(Validation.create());
    }

    default void validateAndCheck(@NonNull Validation validation) {
        this.validate(validation).checkIsValid();
    }
}
