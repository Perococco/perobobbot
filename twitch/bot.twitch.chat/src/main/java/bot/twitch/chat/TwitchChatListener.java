package bot.twitch.chat;

import bot.twitch.chat.message.TwitchMessage;
import lombok.NonNull;

/**
 * @author perococco
 **/
public interface TwitchChatListener {

    void onReceivedMessage(@NonNull TwitchMessage receivedMessage);

    void onPostedMessage(@NonNull TwitchMessage postedMessage);

}
