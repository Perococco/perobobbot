package perococco.bot.twitch.chat.state;

import bot.chat.advanced.AdvancedChat;
import bot.common.lang.Subscription;
import bot.common.lang.fp.Consumer1;
import bot.common.lang.fp.Function1;
import bot.twitch.chat.TwitchChatAlreadyConnected;
import bot.twitch.chat.TwitchChatState;
import bot.twitch.chat.message.from.MessageFromTwitch;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;
import perococco.bot.twitch.chat.IO;
import perococco.bot.twitch.chat.IOWithAdvancedChat;
import perococco.bot.twitch.chat.actions.IOAction;

import java.util.concurrent.CompletionStage;
import java.util.function.Function;

@RequiredArgsConstructor
@Log4j2
public class ConnectionIdentity {


    public enum State {
        DISCONNECTED,
        CONNECTING,
        CONNECTED,
    }


    /**
     * The value of this identity
     */
    private ConnectionValue value = ConnectionValue.disconnected();

    @NonNull
    public TwitchChatState state() {
        return value;
    }

    @Synchronized
    public <T> T mutateAndGet(@NonNull IdentityMutator mutator, @NonNull Function1<? super ConnectionValue, ? extends T> getter) {
        final ConnectionValue oldValue = value;
        this.value = mutator.mutate(oldValue);
        return getter.apply(value);
    }

    @NonNull
    public <T> T applyWithTwitchState(@NonNull Function1<? super TwitchChatState, ? extends T> action) {
        return mutateAndGet(IdentityMutator.IDENTITY, action);
    }

    @NonNull
    public TwitchChatState mutate(@NonNull IdentityMutator mutator) {
        return mutateAndGet(mutator, Function1.identity());
    }


    public void setToConnecting(@NonNull Subscription subscription) {
        this.mutate(v -> {
            if (v.state() != State.DISCONNECTED) {
                throw new TwitchChatAlreadyConnected();
            }
            return v.toBuilder()
                    .state(State.CONNECTING)
                    .subscription(subscription)
                    .build();
        });
    }

    public void setToDisconnected() {
        this.mutate(v -> {
            v.subscription().unsubscribe();
            return ConnectionValue.disconnected();
        });
    }

    public void setToConnected(@NonNull AdvancedChat<MessageFromTwitch> advancedChat) {
        this.mutate(v -> {
            if (v.state() != State.CONNECTING) {
                throw new IllegalStateException("Can not switch to CONNECTED from CONNECTING state");
            }
            return v.toBuilder()
                    .state(State.CONNECTED)
                    .io(new IOWithAdvancedChat(advancedChat))
                    .joinedChannels(ImmutableMap.of())
                    .build();
        });
    }

    @NonNull
    @Synchronized
    public <U,T> CompletionStage<T> composeWithValue(
            @NonNull Function1<? super U,? extends CompletionStage<T>> action,
            @NonNull Function<? super ConnectionValue, ? extends U> getter
    ) {
        return action.apply(getter.apply(value));
    }

    @NonNull
    public <T> CompletionStage<T> composeWithValue(@NonNull Function1<? super ConnectionValue,? extends CompletionStage<T>> action) {
        return composeWithValue(action,v -> v);
    }

    @NonNull
    public <T> CompletionStage<T> composeWithIO(@NonNull Function1<? super IO, ? extends CompletionStage<T>> action) {
        return composeWithValue(action, ConnectionValue::io);
    }

    @NonNull
    public <T> CompletionStage<T> executeWithIO(@NonNull IOAction<T> action) {
        return composeWithValue(action::evaluate, ConnectionValue::io);
    }


    @NonNull
    @Synchronized
    public <U,T> T applyWithValue(
            @NonNull Function1<? super U,? extends T> action,
            @NonNull Function<? super ConnectionValue, ? extends U> getter
    ) {
        return action.apply(getter.apply(value));
    }

    @NonNull
    public <T> T applyWithValue(@NonNull Function1<? super ConnectionValue, ? extends T> action) {
        return applyWithValue(action, c->c);
    }

    @NonNull
    public <T> T applyWithIO(@NonNull Function1<? super IO, ? extends T> action) {
        return applyWithValue(action, ConnectionValue::io);
    }

    public void acceptWithIO(@NonNull Consumer1<? super IO> action) {
        applyWithValue(action.toFunction(), ConnectionValue::io);
    }

}
