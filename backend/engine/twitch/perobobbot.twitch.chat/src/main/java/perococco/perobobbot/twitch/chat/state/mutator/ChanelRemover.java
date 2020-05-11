package perococco.perobobbot.twitch.chat.state.mutator;

import perobobbot.common.lang.MapTool;
import perobobbot.twitch.chat.Channel;
import perobobbot.twitch.chat.message.from.UserState;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perococco.perobobbot.twitch.chat.state.ChannelMutator;

@RequiredArgsConstructor
public class ChanelRemover implements ChannelMutator {

    @NonNull
    private final Channel channelToRemove;

    @Override
    public @NonNull ImmutableMap<Channel, UserState> mutate(@NonNull ImmutableMap<Channel, UserState> currentValue) {
        return MapTool.remove(currentValue,channelToRemove);
    }
}
