package perococco.bot.twitch.chat.state;

import bot.common.lang.fp.UnaryOperator1;
import bot.twitch.chat.Channel;
import bot.twitch.chat.message.from.UserState;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;

public abstract class ChannelMutator implements UnaryOperator1<ConnectionValue> {

    @Override
    public @NonNull ConnectionValue f(@NonNull ConnectionValue connectionValue) {
        final ImmutableMap<Channel,UserState> newChannels = mutate(connectionValue.joinedChannels());
        if (newChannels == connectionValue.joinedChannels()) {
            return connectionValue;
        }
        return connectionValue.toBuilder().joinedChannels(newChannels).build();
    }

    protected abstract ImmutableMap<Channel, UserState> mutate(@NonNull ImmutableMap<Channel,UserState> currentValue);

}
