package perococco.perobobbot.twitch.chat.state.mutator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perococco.perobobbot.twitch.chat.TwitchChannelIO;
import perococco.perobobbot.twitch.chat.state.ConnectedState;
import perococco.perobobbot.twitch.chat.state.ConnectionState;

@RequiredArgsConstructor
public class ChanelAdder implements ConnectedStateMutation {

    @NonNull
    private final TwitchChannelIO twitchChannelIO;

    @Override
    public @NonNull ConnectionState mutate(@NonNull ConnectedState currentValue) {
        return currentValue.withAddedJoinedChannel(twitchChannelIO);
    }

}
