package perococco.bot.twitch.chat;

import bot.chat.advanced.AdvancedChat;
import bot.chat.advanced.event.AdvancedChatEvent;
import bot.common.lang.Listeners;
import bot.common.lang.Subscription;
import bot.twitch.chat.TwitchChat;
import bot.twitch.chat.TwitchChatListener;
import bot.twitch.chat.event.EventBridge;
import bot.twitch.chat.message.from.MessageFromTwitch;
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
    private final Listeners<TwitchChatListener> listeners = new Listeners<>();

    /**
     * A bridge that transform event from {@link AdvancedChat} to {@link TwitchChat} event
     */
    @NonNull
    private final EventBridge eventBridge = new EventBridge(listeners);

    @Override
    @NonNull
    public Subscription addTwitchChatListener(@NonNull TwitchChatListener listener) {
        return listeners.addListener(listener);
    }

    protected void dispatchToTwitchListeners(@NonNull AdvancedChatEvent<MessageFromTwitch> event) {
        event.accept(eventBridge);
    }


}
