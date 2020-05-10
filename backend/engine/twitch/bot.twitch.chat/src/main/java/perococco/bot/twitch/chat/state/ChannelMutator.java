package perococco.bot.twitch.chat.state;

import bot.twitch.chat.Channel;
import bot.twitch.chat.message.from.UserState;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;

public interface ChannelMutator extends IdentityMutator {

    @Override
    @NonNull
    default ConnectionValue mutate(@NonNull ConnectionValue currentValue) {
        final ImmutableMap<Channel,UserState> newChannels = mutate(currentValue.joinedChannels());
        if (newChannels == currentValue.joinedChannels()) {
            return currentValue;
        }
        return currentValue.toBuilder().joinedChannels(newChannels).build();
    }

    @NonNull
    ImmutableMap<Channel, UserState> mutate(@NonNull ImmutableMap<Channel,UserState> currentValue);

}
