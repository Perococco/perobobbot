package perococco.bot.twitch.chat;

import bot.chat.advanced.AdvancedChat;
import bot.chat.advanced.AdvancedChatListener;
import bot.chat.advanced.AdvancedChatManager;
import bot.common.lang.ThrowableTool;
import bot.twitch.chat.*;
import bot.twitch.chat.message.from.MessageFromTwitch;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

@RequiredArgsConstructor
@Log4j2
public class ConnectionIdentity {


    public enum State {
        DISCONNECTED,
        CONNECTING,
        CONNECTED,
    }


    private ConnectionValue value = ConnectionValue.disconnected();

    @NonNull
    private final AdvancedChatListener<MessageFromTwitch> listener;

    public <T> T applyWithTwitchState(@NonNull Function<? super TwitchChatState, ? extends T> action) {
        return mutateAndGet(UnaryOperator.identity(), action);
    }

    @Synchronized
    private <T> T mutateAndGet(@NonNull UnaryOperator<ConnectionValue> mutator, @NonNull Function<? super ConnectionValue, ? extends T> getter) {
        final ConnectionValue oldValue = value;
        this.value = mutator.apply(oldValue);
        return getter.apply(value);
    }

    private void mutate(@NonNull UnaryOperator<ConnectionValue> mutator) {
        mutateAndGet(mutator, Function.identity());
    }

    public void setToConnecting(@NonNull TwitchChatOAuth oAuth, @NonNull AdvancedChatManager<MessageFromTwitch> manager) {
        this.mutate(v -> {
            if (v.state() != State.DISCONNECTED) {
                throw new TwitchChatAlreadyConnected();
            }
            return v.toBuilder().state(State.CONNECTING).oAuth(oAuth).subscription(manager.addChatListener(listener)).build();
        });
    }

    public void setToDisconnected() {
        this.mutate(v -> {
            v.subscription().unsubscribe();
            return ConnectionValue.disconnected();
        });
    }

    @NonNull
    public void setToConnected(@NonNull AdvancedChat<MessageFromTwitch> advancedChat) {
        this.mutate(v -> {
            if (v.state() != State.CONNECTING) {
                throw new IllegalStateException("Can not switch to CONNECTED from CONNECTING state");
            }
            return v.toBuilder()
                    .state(State.CONNECTED)
                    .chat(advancedChat)
                    .joinedChannels(ImmutableSet.of())
                    .build();
        });
    }

    public void addJoinedChannel(@NonNull Channel channel) {
        this.mutate(v -> v.withAddedJoinedChannel(channel));
    }

    public void removeJoinedChannel(@NonNull Channel channel) {
        this.mutate(v -> v.withRemovedJoinedChannel(channel));
    }

    @NonNull
    @Synchronized
    public <T> CompletionStage<T> witChat(@NonNull Function<? super AdvancedChat<MessageFromTwitch>, ? extends CompletionStage<T>> action) {
        try {
            return action.apply(value.chat().orElseThrow(TwicthChatNotConnected::new));
        } catch (Exception e) {
            ThrowableTool.interruptThreadIfCausedByInterruption(e);
            return CompletableFuture.failedFuture(e);
        }
    }

    @NonNull
    public <T, P> CompletionStage<T> witChat(@NonNull BiFunction<? super AdvancedChat<MessageFromTwitch>, ? super P, ? extends CompletionStage<T>> action, @NonNull P parameter) {
        return witChat(c -> action.apply(c, parameter));
    }

}
