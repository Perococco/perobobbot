package perobobbot.program.core;

import lombok.NonNull;
import perobobbot.common.lang.User;
import perococco.perobobbot.program.core.ConsumedExecutionContext;

import java.time.Instant;

/**
 * Information about the context the execution
 * is performed in
 */
public interface ExecutionContext extends ExecutionIO {

    /**
     * @return true if the executing user is at the origin of
     * this execution.
     */
    boolean executingUserIsMe();

    /**
     * @return the user that initiated the execution
     */
    @NonNull
    User getExecutingUser();

    /**
     * @return the id of the user that initiated the execution
     */
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

    /**
     * @return information regarding the channel the execution has been initiated from
     */
    @NonNull
    ChannelInfo getChannelInfo();

    /**
     * @return true if the execution has been consumed and should be ignored afterward
     */
    default boolean isConsumed() {
        return false;
    }

    @NonNull
    default ExecutionContext consumed() {
        return new ConsumedExecutionContext(this);
    }
}
