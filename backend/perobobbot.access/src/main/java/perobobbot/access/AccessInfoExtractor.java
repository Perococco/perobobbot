package perobobbot.access;

import lombok.NonNull;
import perobobbot.lang.User;

import java.time.Instant;

public interface AccessInfoExtractor<P> {

    /**
     * @param parameter the parameter used to start the action
     * @return the user that trying to execute the action using the provided parameter
     */
    @NonNull
    User getExecutor(@NonNull P parameter);

    /**
     * @param parameter the parameter used to start the action
     * @return the time the action using the provided parameter is executed
     */
    @NonNull
    Instant getExecutionTime(@NonNull P parameter);

}
