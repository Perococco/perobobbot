package perobobbot.twitch.chat;

import perobobbot.twitch.chat.event.ReceivedMessage;
import perobobbot.twitch.chat.message.from.PrivMsgFromTwitch;
import lombok.NonNull;
import perococco.perobobbot.twitch.chat.PrivMsgFromTwitchListenerWrapper;

public interface PrivMsgFromTwitchListener {

    void onPrivateMessage(@NonNull ReceivedMessage<PrivMsgFromTwitch> message);

    @NonNull
    default TwitchChatListener toTwitchChatListener() {
        return new PrivMsgFromTwitchListenerWrapper(this);
    }
}