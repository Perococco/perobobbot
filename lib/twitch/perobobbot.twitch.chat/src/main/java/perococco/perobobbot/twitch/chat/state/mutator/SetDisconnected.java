package perococco.perobobbot.twitch.chat.state.mutator;

import lombok.NonNull;
import perobobbot.lang.Mutation;
import perobobbot.lang.Nil;
import perobobbot.lang.Subscription;
import perococco.perobobbot.twitch.chat.state.*;
import perococco.perobobbot.twitch.chat.state.visitor.SubscriptionGetter;

public class SetDisconnected implements Mutation<ConnectionState> {

    @Override
    public @NonNull ConnectionState mutate(@NonNull ConnectionState currentValue) {
        new SubscriptionGetter().applyTo(currentValue).unsubscribe();
        return ConnectionState.disconnected();
    }

}
