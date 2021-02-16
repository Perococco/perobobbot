package perobobbot.validation;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.lang.PerobobbotException;

public class ValidationFailure extends PerobobbotException {

    @Getter
    private final @NonNull ValidationResult validationResult;

    public ValidationFailure(ValidationResult validationResult) {
        super("Validation failed. Invalid fields: "+validationResult.getValidatedFields());
        assert !validationResult.isValid();
        this.validationResult = validationResult;
    }
}
