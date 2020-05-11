package perococco.perobobbot.twitch.chat.state.mutator;

import perobobbot.common.lang.MapTool;
import perobobbot.twitch.chat.Channel;
import perobobbot.twitch.chat.message.from.UserState;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perococco.perobobbot.twitch.chat.state.ChannelMutator;

@RequiredArgsConstructor
public class ChanelAdder implements ChannelMutator {

    @NonNull
    private final UserState userState;

    @Override
    public @NonNull ImmutableMap<Channel, UserState> mutate(@NonNull ImmutableMap<Channel, UserState> currentValue) {
        return MapTool.add(currentValue, userState.getChannel(), userState);
    }
}
