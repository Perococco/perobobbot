package bot.twitch.chat;

import bot.twitch.chat.event.ReceivedMessage;
import bot.twitch.chat.message.from.PrivMsgFromTwitch;
import lombok.NonNull;

public interface PrivMsgFromTwitchListener {

    void onPrivateMessage(@NonNull ReceivedMessage<PrivMsgFromTwitch> message);
}
