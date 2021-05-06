package perobobbot.lang;

import lombok.NonNull;
import lombok.Value;

@Value
public class IdKey<T> {

    @NonNull T userKey;

    @NonNull String random;
}
