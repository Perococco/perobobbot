package bot.twitch.chat;

import bot.twitch.chat.event.ReceivedMessage;
import bot.twitch.chat.message.from.MessageFromTwitch;
import lombok.NonNull;

public interface MessageFromTwitchListener<M extends MessageFromTwitch> {

    void onMessage(@NonNull ReceivedMessage<M> message);

}
