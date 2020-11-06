package perococco.perobobbot.twitch.chat.state;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import perobobbot.twitch.chat.Channel;
import perobobbot.twitch.chat.message.from.UserState;

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
