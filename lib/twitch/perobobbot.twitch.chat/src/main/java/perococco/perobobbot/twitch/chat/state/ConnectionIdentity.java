package perococco.perobobbot.twitch.chat.state;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;
import perobobbot.twitch.chat.TwitchChatState;

@RequiredArgsConstructor
@Log4j2
public class ConnectionIdentity {

    /**
     * The value of this identity
     */
    private ConnectionState value = ConnectionState.disconnected();

    @NonNull
    public TwitchChatState state() {
        return value;
    }

    @Synchronized
    public <T> T operate(@NonNull IdentityOperator<T> operator) {
        final ConnectionState oldValue = value;
        this.value = operator.mutate(oldValue);
        return operator.get(value);
    }

//    @NonNull
//    public <T> T get(@NonNull Function1<? super ConnectionValue, ? extends T> getter) {
//        return operate(IdentityMutator.IDENTITY, getter);
//    }
//
//    @NonNull
//    public TwitchChatState mutate(@NonNull IdentityMutator mutator) {
//        return operate(mutator, Function1.identity());
//    }
//
//    public <U,T> @NonNull CompletionStage<T> getAndLaunchAction(
//            @NonNull Function1<? super ConnectionValue, ? extends U> getter,
//            @NonNull Function1<? super U, ? extends CompletionStage<T>> action
//    ) {
//        return operate(new IdentityMutator() {
//            @Override
//            public @NonNull ConnectionValue mutate(@NonNull ConnectionValue currentValue) {
//                return null;
//            }
//        })
//        this.get(v -> action.apply(getter.apply(v)));
//    }
//
//    public <T> @NonNull CompletionStage<T> launchIOAction(@NonNull TwitchIOAction<T> action) {
//        return getAndLaunchAction(TwitchChatState::io, action::evaluate);
//    }
//
//    public <T,U> @NonNull CompletionStage<U> launchIOActionAndGet(@NonNull TwitchIOAction<T> action,
//                                                                  @NonNull Function2<? super ConnectionValue, ? super T, ? extends U> getter) {
//        return action.evaluate()
//        return getAndLaunchAction(TwitchChatState::io, action::evaluate);
//    }
//

}
