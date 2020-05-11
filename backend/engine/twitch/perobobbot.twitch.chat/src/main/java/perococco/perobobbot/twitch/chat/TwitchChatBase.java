package perococco.perobobbot.twitch.chat;

import perobobbot.common.lang.Listeners;
import perobobbot.common.lang.Subscription;
import perobobbot.twitch.chat.TwitchChat;
import perobobbot.twitch.chat.TwitchChatListener;
import perobobbot.twitch.chat.event.TwitchChatEvent;
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


    protected void warnListeners(@NonNull TwitchChatEvent event) {
        listeners.warnListeners(TwitchChatListener::onTwitchChatEvent, event);
    }

    @Override
    @NonNull
    public Subscription addTwitchChatListener(@NonNull TwitchChatListener listener) {
        return listeners.addListener(listener);
    }


}
