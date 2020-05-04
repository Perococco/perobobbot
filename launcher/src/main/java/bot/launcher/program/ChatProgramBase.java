package bot.launcher.program;

import bot.twitch.chat.TwitchChatIO;
import bot.twitch.chat.event.ReceivedMessage;
import bot.twitch.chat.message.from.PrivMsgFromTwitch;
import lombok.NonNull;

public abstract class ChatProgramBase implements ChatProgram {

    @Override
    public final boolean handleCommand(@NonNull TwitchChatIO twitchChatIO, @NonNull ReceivedMessage<PrivMsgFromTwitch> reception, @NonNull Command command) {
        if (isOneOfMyCommand(command)) {
            return this.doHandleCommand(twitchChatIO, reception, command);
        }
        return false;
    }

    protected abstract boolean doHandleCommand(@NonNull TwitchChatIO twitchChatIO, @NonNull ReceivedMessage<PrivMsgFromTwitch> reception, @NonNull Command command);

    protected abstract boolean isOneOfMyCommand(@NonNull Command command);

}
