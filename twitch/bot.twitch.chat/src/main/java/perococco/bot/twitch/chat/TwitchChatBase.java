package perococco.bot.twitch.chat;

import bot.common.lang.Listeners;
import bot.common.lang.Subscription;
import bot.twitch.chat.PrivMsgFromTwitchListener;
import bot.twitch.chat.TwitchChat;
import bot.twitch.chat.TwitchChatListener;
import bot.twitch.chat.TwitchChatState;
import bot.twitch.chat.event.ReceivedMessage;
import bot.twitch.chat.event.ReceivedMessageExtractor;
import bot.twitch.chat.event.TwitchChatEvent;
import bot.twitch.chat.message.from.PrivMsgFromTwitch;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;

import java.time.Instant;
import java.util.Optional;

/**
 * Implements basic operations with listeners
 *
 * @author perococco
 **/
public abstract class TwitchChatBase implements TwitchChat {

    /**
     * The listeners to this {@link TwitchChat}
     */
    @Getter(AccessLevel.PROTECTED)
    private final Listeners<TwitchChatListener> listeners = new Listeners<>();

    private final ReceivedMessageExtractor extractor = ReceivedMessageExtractor.create();

    protected void warnListeners(@NonNull TwitchChatEvent event) {
        listeners.warnListeners(TwitchChatListener::onTwitchChatEvent, event);
    }

    @Override
    @NonNull
    public Subscription addTwitchChatListener(@NonNull TwitchChatListener listener) {
        return listeners.addListener(listener);
    }

    @Override
    public @NonNull Subscription addPrivateMessageListener(@NonNull PrivMsgFromTwitchListener listener) {
        return addTwitchChatListener(
                e -> e.accept(extractor)
                      .flatMap(this::castToPrivateMessage)
                      .ifPresent(listener::onPrivateMessage)
        );
    }

    @NonNull
    private Optional<ReceivedMessage<PrivMsgFromTwitch>> castToPrivateMessage(@NonNull ReceivedMessage<?> receivedMessage) {
        if (receivedMessage.message() instanceof PrivMsgFromTwitch) {
            final TwitchChatState twitchChatState = receivedMessage.state();
            final Instant receptionTime = receivedMessage.receptionTime();
            final PrivMsgFromTwitch message = (PrivMsgFromTwitch) receivedMessage.message();
            return Optional.of(new ReceivedMessage<>(twitchChatState,receptionTime,message));
        }
        return Optional.empty();
    }

}
