package perobobbot.program.core;

import perobobbot.common.lang.User;
import lombok.NonNull;

import java.time.Instant;

public interface ExecutionContext extends ProgramIO {

    /**
     * @return the user that initiate the execution
     */
    @NonNull
    User getExecutingUser();

    /**
     * @return the instant of reception du message
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

}
