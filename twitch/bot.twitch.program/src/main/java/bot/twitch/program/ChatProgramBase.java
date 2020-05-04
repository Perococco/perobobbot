package bot.twitch.program;

import bot.twitch.chat.TwitchChatIO;
import lombok.NonNull;

public abstract class ChatProgramBase implements ChatProgram {

    @Override
    public final boolean handleCommand(@NonNull TwitchChatIO twitchChatIO, @NonNull ProgramCommand command) {
        if (isOneOfMyCommand(command)) {
            return this.doHandleCommand(twitchChatIO, command);
        }
        return false;
    }

    protected abstract boolean doHandleCommand(@NonNull TwitchChatIO twitchChatIO, @NonNull ProgramCommand command);

    protected abstract boolean isOneOfMyCommand(@NonNull ProgramCommand command);

}
