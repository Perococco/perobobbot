package perococco.perobobbot.twitch.chat.state.mutator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perococco.perobobbot.twitch.chat.TwitchMessageChannelIO;
import perococco.perobobbot.twitch.chat.state.ConnectedState;
import perococco.perobobbot.twitch.chat.state.ConnectionState;

@RequiredArgsConstructor
public class ChanelAdder implements ConnectedStateMutation {

    @NonNull
    private final TwitchMessageChannelIO twitchChannelIO;

    @Override
    public @NonNull ConnectionState mutate(@NonNull ConnectedState currentValue) {
        return currentValue.withAddedJoinedChannel(twitchChannelIO);
    }

}
