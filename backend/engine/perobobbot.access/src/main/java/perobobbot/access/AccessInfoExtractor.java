package perobobbot.access;

import lombok.NonNull;
import perobobbot.lang.User;

import java.time.Instant;

public interface AccessInfoExtractor<P> {

    @NonNull
    User getExecutor(@NonNull P parameter);

    @NonNull
    Instant getExecutionTime(@NonNull P parameter);

}
