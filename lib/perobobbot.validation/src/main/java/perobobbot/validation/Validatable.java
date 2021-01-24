package perobobbot.validation;

import lombok.NonNull;

public interface Validatable {

    @NonNull
    Validation validate(@NonNull Validation validation);
}
