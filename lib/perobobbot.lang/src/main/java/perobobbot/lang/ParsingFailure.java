package perobobbot.lang;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class ParsingFailure extends PerobobbotException {

    private final @NonNull String value;

    private final @NonNull Class<?> targetType;

    public ParsingFailure(@NonNull String value, @NonNull Class<?> targetType) {
        super("Could not parse '"+value+"' to type '"+targetType+"'");
        this.value = value;
        this.targetType = targetType;
    }

    public ParsingFailure(@NonNull String value, @NonNull Class<?> targetType, @NonNull Throwable cause) {
        this(value,targetType);
        initCause(cause);
    }
}
