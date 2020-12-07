package perococco.perobobbot.twitch.chat;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.MessageListener;
import perobobbot.twitch.chat.PrivMsgFromTwitchListener;
import perobobbot.twitch.chat.TwitchChatListener;
import perobobbot.twitch.chat.TwitchChatState;
import perobobbot.twitch.chat.event.ReceivedMessage;
import perobobbot.twitch.chat.event.ReceivedMessageExtractor;
import perobobbot.twitch.chat.event.TwitchChatEvent;
import perobobbot.twitch.chat.message.from.PrivMsgFromTwitch;

import java.time.Instant;
import java.util.Optional;

@RequiredArgsConstructor
public class PrivMsgFromTwitchListenerWrapper implements TwitchChatListener {

    @NonNull
    private final PrivMsgFromTwitchListener delegate;

    private final ReceivedMessageExtractor extractor = ReceivedMessageExtractor.create();

    @Override
    public void onTwitchChatEvent(@NonNull TwitchChatEvent event) {
        event.accept(extractor)
             .flatMap(this::castToPrivateMessage)
             .ifPresent(delegate::onPrivateMessage);
    }

    @NonNull
    private Optional<ReceivedMessage<PrivMsgFromTwitch>> castToPrivateMessage(@NonNull ReceivedMessage<?> receivedMessage) {
        if (receivedMessage.getMessage() instanceof PrivMsgFromTwitch) {
            final TwitchChatState twitchChatState = receivedMessage.getState();
            final Instant receptionTime = receivedMessage.getReceptionTime();
            final PrivMsgFromTwitch message = (PrivMsgFromTwitch) receivedMessage.getMessage();
            return Optional.of(new ReceivedMessage<>(twitchChatState,receptionTime,message));
        }
        return Optional.empty();
    }

}
