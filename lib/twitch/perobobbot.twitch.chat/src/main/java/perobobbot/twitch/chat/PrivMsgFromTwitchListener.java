package perobobbot.twitch.chat;

import lombok.NonNull;
import perobobbot.lang.fp.Consumer1;
import perobobbot.lang.fp.Function1;
import perobobbot.twitch.chat.event.ReceivedMessage;
import perobobbot.twitch.chat.message.from.PrivMsgFromTwitch;
import perococco.perobobbot.twitch.chat.PrivMsgFromTwitchListenerWrapper;

public interface PrivMsgFromTwitchListener {

    void onPrivateMessage(@NonNull ReceivedMessage<PrivMsgFromTwitch> message);

    @NonNull
    default TwitchChatListener toTwitchChatListener() {
        return new PrivMsgFromTwitchListenerWrapper(this);
    }




    @NonNull
    static <T> PrivMsgFromTwitchListener with(
            @NonNull Function1<? super ReceivedMessage<PrivMsgFromTwitch>, ? extends T> converter,
            @NonNull Consumer1<T> consumer) {
        final Consumer1<? super ReceivedMessage<PrivMsgFromTwitch>> listener = converter.thenAccept(consumer);
        return listener::f;
    }

}
