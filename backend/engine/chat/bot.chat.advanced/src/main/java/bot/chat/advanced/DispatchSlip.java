package bot.chat.advanced;

import lombok.NonNull;

import java.time.Instant;

/**
 * Slip for command sent to the chat
 */
public interface DispatchSlip {

    /**
     * @return the sent command
     */
    @NonNull
    Command getSentCommand();

    /**
     * @return the time when the command was sent
     */
    @NonNull
    Instant getDispatchingTime();

}
