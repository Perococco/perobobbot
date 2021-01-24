package perobobbot.fx.dialog;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import perobobbot.validation.ValidationResult;

import java.util.Optional;

@Value
public class DialogState<O> {

    public static <O> DialogState<O> empty() {
        return new DialogState<>(ValidationResult.empty(), null);
    }

    @NonNull ValidationResult validationResult;

    @Getter(AccessLevel.NONE) O value;

    public boolean isValid() {
        return value != null && validationResult.isValid();
    }

    @NonNull
    public Optional<O> getValue() {
        if (isValid()) {
            return Optional.ofNullable(value);
        }
        return Optional.empty();
    }

}
