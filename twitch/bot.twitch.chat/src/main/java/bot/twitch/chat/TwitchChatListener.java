package bot.twitch.chat;

import bot.twitch.chat.message.from.MessageFromTwitch;
import bot.twitch.chat.message.to.MessageToTwitch;
import lombok.NonNull;

/**
 * @author perococco
 **/
public interface TwitchChatListener {

    void onMessageFromTwitch(@NonNull MessageFromTwitch messageFromTwitch);

    void onMessageToTwitch(@NonNull MessageToTwitch messageToTwitch);

}
