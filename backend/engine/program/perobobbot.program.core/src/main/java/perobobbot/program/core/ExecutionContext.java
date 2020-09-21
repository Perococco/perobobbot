package perobobbot.program.core;

import lombok.NonNull;
import perobobbot.common.lang.User;
import perococco.perobobbot.program.core.ConsumedExecutionContext;

import java.time.Instant;

public interface ExecutionContext extends ExecutionIO {

    boolean executingUserIsMe();

    /**
     * @return the user that initiate the execution
     */
    @NonNull
    User getExecutingUser();

    @NonNull
    default String getExecutingUserId() {
        return getExecutingUser().getUserId();
    }

    /**
     * @return the instant of reception of the message
     */
    @NonNull
    Instant getReceptionTime();

    /**
     * @return the raw payload received from the chat (for Twitch this might include badges)
     */
    @NonNull
    String getRawPayload();

    /**
     * @return the content of the private message
     */
    @NonNull
    String getMessage();

    default boolean isConsumed() {
        return false;
    }

    @NonNull
    default ExecutionContext consumed() {
        return new ConsumedExecutionContext(this);
    }
}
