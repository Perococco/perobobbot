package perococco.bot.twitch.chat.state;

import bot.common.lang.MapTool;
import bot.twitch.chat.Channel;
import bot.twitch.chat.message.from.UserState;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChanelRemover extends ChannelMutator {

    @NonNull
    private final Channel channelToRemove;

    @Override
    protected ImmutableMap<Channel, UserState> mutate(@NonNull ImmutableMap<Channel, UserState> currentValue) {
        return MapTool.remove(currentValue,channelToRemove);
    }
}
