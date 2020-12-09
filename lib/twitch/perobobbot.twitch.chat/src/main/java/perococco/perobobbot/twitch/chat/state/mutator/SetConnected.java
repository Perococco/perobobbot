package perococco.perobobbot.twitch.chat.state.mutator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.chat.advanced.AdvancedChat;
import perobobbot.lang.Mutation;
import perobobbot.twitch.chat.message.from.MessageFromTwitch;
import perococco.perobobbot.twitch.chat.TwitchIOWithAdvancedChat;
import perococco.perobobbot.twitch.chat.state.ConnectionState;

@RequiredArgsConstructor
public class SetConnected implements Mutation<ConnectionState> {

    private final @NonNull String userId;

    private final @NonNull AdvancedChat<MessageFromTwitch> advancedChat;

    @Override
    public @NonNull ConnectionState mutate(@NonNull ConnectionState currentValue) {
        return currentValue.asConnectingState()
                           .orElseThrow(() -> new IllegalStateException("Can only switch to CONNECTED from CONNECTING state"))
                           .toConnected(new TwitchIOWithAdvancedChat(advancedChat), userId);

    }
}
