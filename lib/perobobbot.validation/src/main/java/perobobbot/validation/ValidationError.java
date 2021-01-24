package perobobbot.validation;

import lombok.NonNull;
import lombok.Value;

@Value
public class ValidationError {

    @NonNull String fieldName;

    @NonNull String errorType;
}
