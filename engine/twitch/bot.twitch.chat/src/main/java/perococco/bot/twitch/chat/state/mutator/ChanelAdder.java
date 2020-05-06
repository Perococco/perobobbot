package perococco.bot.twitch.chat.state.mutator;

import bot.common.lang.MapTool;
import bot.twitch.chat.Channel;
import bot.twitch.chat.message.from.UserState;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perococco.bot.twitch.chat.state.ChannelMutator;

@RequiredArgsConstructor
public class ChanelAdder implements ChannelMutator {

    @NonNull
    private final UserState userState;

    @Override
    public @NonNull ImmutableMap<Channel, UserState> mutate(@NonNull ImmutableMap<Channel, UserState> currentValue) {
        return MapTool.add(currentValue,userState.channel(),userState);
    }
}
