package perococco.perobobbot.twitch.chat.state.visitor;

import lombok.NonNull;
import perobobbot.lang.Subscription;
import perococco.perobobbot.twitch.chat.state.ConnectedState;
import perococco.perobobbot.twitch.chat.state.ConnectingState;
import perococco.perobobbot.twitch.chat.state.ConnectionState;
import perococco.perobobbot.twitch.chat.state.DisconnectedState;

public class SubscriptionGetter implements ConnectionState.Visitor<Subscription> {

    public static @NonNull SubscriptionGetter create() {
        return new SubscriptionGetter();
    }

    @Override
    public @NonNull Subscription visit(@NonNull DisconnectedState state) {
        return Subscription.NONE;
    }

    @Override
    public @NonNull Subscription visit(@NonNull ConnectingState state) {
        return state.getSubscription();
    }

    @Override
    public @NonNull Subscription visit(@NonNull ConnectedState state) {
        return state.getSubscription();
    }
}
