package perococco.perobobbot.twitch.chat.state.mutator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.chat.advanced.AdvancedChat;
import perobobbot.lang.Mutation;
import perobobbot.lang.Subscription;
import perobobbot.twitch.chat.message.TagKey;
import perobobbot.twitch.chat.message.from.GlobalUserState;
import perobobbot.twitch.chat.message.from.MessageFromTwitch;
import perococco.perobobbot.twitch.chat.IOWithAdvancedChat;
import perococco.perobobbot.twitch.chat.state.ConnectedState;
import perococco.perobobbot.twitch.chat.state.ConnectionState;
import perococco.perobobbot.twitch.chat.state.IdentityMutator;
import perococco.perobobbot.twitch.chat.state.visitor.ConnectingPredicate;
import perococco.perobobbot.twitch.chat.state.visitor.SubscriptionGetter;

@RequiredArgsConstructor
public class SetConnected implements Mutation<ConnectionState> {

    private final @NonNull GlobalUserState globalUserState;

    private final @NonNull AdvancedChat<MessageFromTwitch> advancedChat;

    @Override
    public @NonNull ConnectionState mutate(@NonNull ConnectionState currentValue) {
        if (!ConnectingPredicate.create().test(currentValue)) {
            throw new IllegalStateException("Can only switch to CONNECTED from CONNECTING state");
        }
        final Subscription subscription = SubscriptionGetter.create().applyTo(currentValue);
        final String userId = globalUserState.getTag(TagKey.USER_ID);
        return ConnectedState.create(
                new IOWithAdvancedChat(advancedChat),
                userId,
                subscription
        );

    }
}
