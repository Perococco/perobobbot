package bot.launcher.program;

import bot.twitch.chat.TwitchChatIO;
import bot.twitch.chat.event.ReceivedMessage;
import bot.twitch.chat.message.from.PrivMsgFromTwitch;
import lombok.NonNull;

public interface ChatProgram {

    /**
     * @param twitchChatIO the Twitch Chat IO to send a message
     * @param command the command received from Twitch
     * @return true if the command should not be processed by other program
     */
    boolean handleCommand(@NonNull TwitchChatIO twitchChatIO, @NonNull ReceivedMessage<PrivMsgFromTwitch> reception, @NonNull Command command);
}
