package perobobbot.twitch.chat;

import perobobbot.twitch.chat.event.ReceivedMessage;
import perobobbot.twitch.chat.message.from.MessageFromTwitch;
import lombok.NonNull;

public interface MessageFromTwitchListener<M extends MessageFromTwitch> {

    void onMessage(@NonNull ReceivedMessage<M> message);

}
