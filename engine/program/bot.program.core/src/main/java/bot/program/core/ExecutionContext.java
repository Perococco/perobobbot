package bot.program.core;

import bot.common.lang.User;
import bot.common.lang.UserRole;
import lombok.NonNull;

import java.time.Instant;

public interface ExecutionContext extends ProgramIO {

    /**
     * @return the user that initiate the execution
     */
    @NonNull
    User executingUser();

    /**
     * @return the instant of reception du message
     */
    @NonNull
    Instant receptionTime();

    /**
     * @return the raw payload received from the chat (for Twitch this might include badges)
     */
    @NonNull
    String rawPayload();

    /**
     * @return the content of the private message
     */
    @NonNull
    String message();

}
