package bot.twitch.program;

import bot.twitch.chat.TwitchChatIO;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;

public interface ChatProgram {

    @NonNull
    String name();

    @NonNull
    ImmutableSet<String> commands();

    /**
     * @param twitchChatIO the Twitch Chat IO to send a message
     * @param command the command received from Twitch
     * @return true if the command should not be processed by other program
     */
    boolean handleCommand(
            @NonNull TwitchChatIO twitchChatIO,
            @NonNull ProgramCommand command
    );
}
