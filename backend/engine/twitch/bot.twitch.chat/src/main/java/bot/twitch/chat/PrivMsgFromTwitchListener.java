package bot.twitch.chat;

import bot.twitch.chat.event.ReceivedMessage;
import bot.twitch.chat.message.from.PrivMsgFromTwitch;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perococco.bot.twitch.chat.PrivMsgFromTwitchListenerWrapper;

public interface PrivMsgFromTwitchListener {

    void onPrivateMessage(@NonNull ReceivedMessage<PrivMsgFromTwitch> message);

    @NonNull
    default TwitchChatListener toTwitchChatListener() {
        return new PrivMsgFromTwitchListenerWrapper(this);
    }
}
