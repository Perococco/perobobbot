package perobobbot.server.config.validation;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.Value;

@Value
public class ValidationErrorResponse {

    @NonNull ImmutableList<Violation> errors;
}
