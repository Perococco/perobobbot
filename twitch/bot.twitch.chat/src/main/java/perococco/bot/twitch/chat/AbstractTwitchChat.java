package perococco.bot.twitch.chat;

import bot.chat.advanced.*;
import bot.chat.advanced.event.AdvancedChatEvent;
import bot.common.lang.Listeners;
import bot.common.lang.Subscription;
import bot.twitch.chat.*;
import bot.twitch.chat.event.EventBridge;
import bot.twitch.chat.message.from.MessageFromTwitch;
import bot.twitch.chat.message.from.Part;
import bot.twitch.chat.message.from.UserState;
import bot.twitch.chat.message.to.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;

import java.util.concurrent.CompletionStage;

/**
 * @author perococco
 **/
public abstract class AbstractTwitchChat implements TwitchChatManager {

    private final Listeners<TwitchChatListener> listeners = new Listeners<>();

    @NonNull
    private final EventBridge eventBridge = new EventBridge(listeners);

    @Override
    @NonNull
    public Subscription addTwitchChatListener(@NonNull TwitchChatListener listener) {
        return listeners.addListener(listener);
    }

    @Override
    public @NonNull CompletionStage<TwitchReceiptSlip<UserState>> join(@NonNull Channel channel) {
        return sendToChat(new Join(channel));
    }

    @Override
    public @NonNull CompletionStage<TwitchReceiptSlip<Part>> part(@NonNull Channel channel) {
        return sendToChat(new bot.twitch.chat.message.to.Part(channel));
    }

    @Override
    public @NonNull CompletionStage<TwitchDispatchSlip> message(@NonNull Channel channel, @NonNull String message) {
        return sendToChat(new PrivMsg(channel,message));
    }

    protected void dispatchToTwitchListeners(@NonNull AdvancedChatEvent<MessageFromTwitch> event) {
        event.accept(eventBridge);
    }

    @NonNull
    protected abstract CompletionStage<TwitchDispatchSlip> sendToChat(@NonNull Command command);

    @NonNull
    protected abstract <A> CompletionStage<TwitchReceiptSlip<A>> sendToChat(@NonNull Request<A> request);
}
