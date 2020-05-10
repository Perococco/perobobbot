package perococco.bot.twitch.chat;

import bot.twitch.chat.PrivMsgFromTwitchListener;
import bot.twitch.chat.TwitchChatListener;
import bot.twitch.chat.TwitchChatState;
import bot.twitch.chat.event.ReceivedMessage;
import bot.twitch.chat.event.ReceivedMessageExtractor;
import bot.twitch.chat.event.TwitchChatEvent;
import bot.twitch.chat.message.from.PrivMsgFromTwitch;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

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
