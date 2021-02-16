package perococco.perobobbot.validation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.validation.ValidationError;
import perobobbot.validation.ValidationResult;

import java.util.*;
import java.util.stream.Collector;

public class ValidationContext {

    private static final Collector<Map.Entry<String, Set<ValidationError>>, ?, ImmutableMap<String, ImmutableList<ValidationError>>> COLLECTOR
            = ImmutableMap.toImmutableMap(
            Map.Entry::getKey,
            e -> ImmutableList.copyOf(e.getValue())
    );

    @NonNull
    private final Set<String> validatedFields = new HashSet<>();

    @NonNull
    private final Map<String, Set<ValidationError>> errors = new HashMap<>();

    public void addValidatedField(@NonNull String validatedField) {
        this.validatedFields.add(validatedField);
    }

    public void addError(@NonNull ValidationError error) {
        this.errors.computeIfAbsent(error.getFieldName(), f -> new LinkedHashSet<>()).add(error);
    }

    public void addError(@NonNull String fieldName, @NonNull String errorType) {
        this.addError(new ValidationError(fieldName,errorType));
    }

    @NonNull
    public ValidationResult getResult() {
        final ImmutableMap<String, ImmutableList<ValidationError>> errorByField = errors.entrySet()
                                                                                        .stream()
                                                                                        .collect(COLLECTOR);
        return new ValidationResult(ImmutableSet.copyOf(validatedFields),errorByField);
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    public boolean isFieldValid(String fieldName) {
        return errors.get(fieldName) == null;
    }
}
