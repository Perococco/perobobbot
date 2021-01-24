package perobobbot.validation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Getter
public class ValidationResult {

    @NonNull
    private final ImmutableSet<String> validatedFields;

    @NonNull
    private final ImmutableMap<String, ImmutableList<ValidationError>> errors;

    @NonNull
    public static ValidationResult empty() {
        return new ValidationResult(ImmutableSet.of(), ImmutableMap.of());
    }

    @NonNull
    public boolean isValid() {
        return errors.isEmpty();
    }

    @NonNull
    public ImmutableList<ValidationError> getErrors(@NonNull String fieldName) {
        return errors.getOrDefault(fieldName,ImmutableList.of());
    }

    @NonNull
    public Optional<ValidationError> getFirstError(@NonNull String fieldName) {
        final ImmutableList<ValidationError> fieldErrors = errors.get(fieldName);
        if (fieldErrors == null || fieldErrors.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(fieldErrors.get(0));
    }


}
