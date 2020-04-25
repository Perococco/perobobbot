package perococco.bot.twitch.chat.state;

import bot.common.lang.MapTool;
import bot.common.lang.fp.UnaryOperator1;
import bot.twitch.chat.Channel;
import bot.twitch.chat.message.from.UserState;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChanelAdder extends ChannelMutator {

    @NonNull
    private final UserState userState;

    @Override
    protected ImmutableMap<Channel, UserState> mutate(@NonNull ImmutableMap<Channel, UserState> currentValue) {
        return MapTool.add(currentValue,userState.channel(),userState);
    }
}
