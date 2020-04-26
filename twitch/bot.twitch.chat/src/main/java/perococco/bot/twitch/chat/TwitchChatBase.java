package perococco.bot.twitch.chat;

import bot.common.lang.Listeners;
import bot.common.lang.Subscription;
import bot.twitch.chat.PrivMsgFromTwitchListener;
import bot.twitch.chat.TwitchChat;
import bot.twitch.chat.TwitchChatListener;
import bot.twitch.chat.event.ReceivedMessageExtractor;
import bot.twitch.chat.event.TwitchChatEvent;
import bot.twitch.chat.message.from.PrivMsgFromTwitch;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;

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
                      .flatMap(r -> r.castMessageTo(PrivMsgFromTwitch.class))
                      .ifPresent(listener::onPrivateMessage)
        );
    }

}
