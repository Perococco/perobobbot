package perobobbot.twitch.chat;

import lombok.NonNull;
import perobobbot.twitch.chat.event.ReceivedMessage;
import perobobbot.twitch.chat.message.from.MessageFromTwitch;

public interface MessageFromTwitchListener<M extends MessageFromTwitch> {

    void onMessage(@NonNull ReceivedMessage<M> message);

}
